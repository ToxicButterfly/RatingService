package com.example.ratingservice.service.impl;

import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.dto.RatingsDto;
import com.example.ratingservice.exception.RatingNotFoundException;
import com.example.ratingservice.feign.DriverFeignInterface;
import com.example.ratingservice.feign.PassengerFeignInterface;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.repo.RatingRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.ratingservice.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {

    @Mock
    private RatingRepo ratingRepo;
    @Mock
    private PassengerFeignInterface passengerFeignInterface;
    @Mock
    private DriverFeignInterface driverFeignInterface;
    @InjectMocks
    private RatingServiceImpl ratingService;

    @Test
    void saveRating() {
        doReturn(getDefaultRating()).when(ratingRepo).save(any(Rating.class));

        ratingService.saveRating(getDefaultRating());

        verify(ratingRepo).save(any(Rating.class));
    }

    @Test
    void getAverageWhenUserExist() {
        RatingDto ratingDto = getDefaultRatingDto();
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultRating(), getDefaultRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        RatingDto response = ratingService.getAverage(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        assertEquals(ratingDto, response);
        verify(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);
    }

    @Test
    void getAverageWhenUserNotExist() {
        doReturn(Optional.empty()).when(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
        assertThrows(RatingNotFoundException.class, () -> ratingService.getAverage(DEFAULT_PASSENGER_ROLE, DEFAULT_ID));
        verify(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
    }

    @Test
    void getRatingWhenExist() {
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultRating(), getDefaultRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        RatingsDto ratings = ratingService.getRating(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        assertEquals(new RatingsDto(list), ratings);
        verify(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);
    }

    @Test
    void getRatingWhenNotExist() {
        doReturn(Optional.empty()).when(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
        assertThrows(RatingNotFoundException.class, () -> ratingService.getRating(DEFAULT_PASSENGER_ROLE, DEFAULT_ID));
        verify(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
    }

    @Test
    void updateRating() {
        doReturn(ResponseEntity.ok(getDefaultRatingResponse())).when(passengerFeignInterface).askOpinion(DEFAULT_ID);
        doReturn(ResponseEntity.ok(getDefaultRatingResponse())).when(driverFeignInterface).askOpinion(DEFAULT_ID);
        doReturn(Optional.of(Arrays.asList(getDefaultRating(), getDefaultRating()))).when(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);
        doReturn(Optional.of(Arrays.asList(getDefaultPassengerRating(), getDefaultPassengerRating()))).when(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
        doNothing().when(driverFeignInterface).updateRating(getDefaultUpdateRatingRequest(), DEFAULT_ID);
        doNothing().when(passengerFeignInterface).updateRating(getDefaultUpdateRatingRequest(), DEFAULT_ID);

        ratingService.updateRating(getDefaultDelegationFromRidesRequest());

        verify(passengerFeignInterface).askOpinion(DEFAULT_ID);
        verify(driverFeignInterface).askOpinion(DEFAULT_ID);
        verify(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);
        verify(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);
        verify(ratingRepo, times(2)).save(any(Rating.class));

    }
}
