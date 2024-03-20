package com.example.ratingservice.service.impl;

import com.example.ratingservice.dto.*;
import com.example.ratingservice.exception.RatingNotFoundException;
import com.example.ratingservice.repo.RatingRepo;
import com.example.ratingservice.feign.DriverFeignInterface;
import com.example.ratingservice.feign.PassengerFeignInterface;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import com.example.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    final RatingRepo ratingRepo;
    final PassengerFeignInterface passengerFeignInterface;
    final DriverFeignInterface driverFeignInterface;

    public Rating saveRating(Rating rating) {
        ratingRepo.save(rating);
        return rating;
    }

    @SneakyThrows
    public RatingDto getAverage(Role role, int id) {
        List<Rating> ratings = ratingRepo.findByRoleAndUid(role, id)
                .orElseThrow(() -> new RatingNotFoundException("There's no records for such person"));
        Float sum = 0F;
        for(Rating rating: ratings) {
            sum += rating.getRatingScore();
        }
        return new RatingDto(role, id,sum/ratings.size());
    }

    @SneakyThrows
    public RatingsDto getRating(Role role, int id) {
        List<Rating> ratings = ratingRepo.findByRoleAndUid(role, id)
                .orElseThrow(() -> new RatingNotFoundException("There's no records for such person"));
        return new RatingsDto(ratings);
    }

    public void updateRating(DelegationFromRidesRequest request) {
        RatingResponse passengerResponse = passengerFeignInterface.askOpinion(request.getPassId(), request.getToken()).getBody();
        RatingResponse driverResponse = driverFeignInterface.askOpinion(request.getDriverId(), request.getToken()).getBody();

        Rating driverRating = Rating.builder()
                .role(Role.valueOf("Driver"))
                .rideId(request.getRideId())
                .uid(request.getDriverId())
                .ratingScore(passengerResponse.getRating().floatValue())
                .build();
        Rating passengerRating = Rating.builder()
                .role(Role.valueOf("Passenger"))
                .rideId(request.getRideId())
                .uid(request.getPassId())
                .ratingScore(driverResponse.getRating().floatValue())
                .build();

        Float driverAverage = getNewAverage(Role.valueOf("Driver"), request.getDriverId());
        Float passengerAverage = getNewAverage(Role.valueOf("Passenger"), request.getPassId());
        driverFeignInterface.updateRating(new UpdateRatingRequest(driverAverage), request.getDriverId(), request.getToken());
        passengerFeignInterface.updateRating(new UpdateRatingRequest(passengerAverage), request.getPassId(), request.getToken());
        ratingRepo.save(driverRating);
        ratingRepo.save(passengerRating);
        log.info("Rating updated successfully!");
    }

    private Float getNewAverage(Role role, Integer id) {

        List<Rating> allRatings = ratingRepo.findByRoleAndUid(role, id).get();
        Float avg = 0F;
        for (Rating rating: allRatings) {
            avg += rating.getRatingScore();
        }
        avg = avg/allRatings.size();
        return avg;
    }
}
