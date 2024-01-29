package com.example.ratingservice.service;

import com.example.ratingservice.dao.RatingDAO;
import com.example.ratingservice.dto.DelegationFromRidesRequest;
import com.example.ratingservice.dto.RatingDTO;
import com.example.ratingservice.dto.RatingResponse;
import com.example.ratingservice.dto.UpdateRatingRequest;
import com.example.ratingservice.feign.DriverFeignInterface;
import com.example.ratingservice.feign.PassengerFeignInterface;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    final RatingDAO ratingDAO;
    final PassengerFeignInterface passengerFeignInterface;
    final DriverFeignInterface driverFeignInterface;

    public ResponseEntity<Rating> saveRating(Rating rating) {
        ratingDAO.save(rating);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }


    public ResponseEntity<RatingDTO> getAverage(Role role, int id) {
        Optional<List<Rating>> dbResult = ratingDAO.findByRoleAndUid(role, id);
        //TODO make UserNotFoundException
        List<Rating> ratings = dbResult.get();
        Float sum = 0F;
        for(Rating rating: ratings) {
            sum += rating.getRatingScore();
        }
        return new ResponseEntity<>(new RatingDTO(role, id,sum/ratings.size()), HttpStatus.OK);
    }

    public ResponseEntity<List<Rating>> getRating(Role role, int id) {
        Optional<List<Rating>> dbResult = ratingDAO.findByRoleAndUid(role, id);
        //TODO make UserNotFoundException
        List<Rating> ratings = dbResult.get();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    public void updateRating(DelegationFromRidesRequest request) {
        RatingResponse passengerResponse = passengerFeignInterface.askOpinion(request.getPassId()).getBody();
        RatingResponse driverResponse = driverFeignInterface.askOpinion(request.getDriverId()).getBody();
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
        driverFeignInterface.updateRating(new UpdateRatingRequest(driverAverage), request.getDriverId());
        passengerFeignInterface.updateRating(new UpdateRatingRequest(passengerAverage), request.getPassId());
        ratingDAO.save(driverRating);
        ratingDAO.save(passengerRating);
        log.info("Rating updated successfully!");
    }

    private Float getNewAverage(Role role, Integer id) {

        List<Rating> allRatings = ratingDAO.findByRoleAndUid(role, id).get();
        Float avg = 0F;
        for (Rating rating: allRatings) {
            avg += rating.getRatingScore();
        }
        avg = avg/allRatings.size();
        return avg;
    }
}
