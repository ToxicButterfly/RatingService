package com.example.ratingservice.util;

import com.example.ratingservice.dto.DelegationFromRidesRequest;
import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.dto.RatingResponse;
import com.example.ratingservice.dto.UpdateRatingRequest;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import lombok.experimental.UtilityClass;
import org.hibernate.sql.Update;

@UtilityClass
public class TestUtils {

    public final Integer DEFAULT_ID = 1;
    public final Role DEFAULT_DRIVER_ROLE = Role.valueOf("Driver");
    public final Role DEFAULT_PASSENGER_ROLE = Role.valueOf("Passenger");
    public final Float DEFAULT_RATING = 5.0F;
    public final Integer DEFAULT_INTEGER_RATING = 5;

    public Rating getDefaultRating() {
        return Rating.builder()
                .id(DEFAULT_ID)
                .role(DEFAULT_DRIVER_ROLE)
                .rideId(DEFAULT_ID)
                .uid(DEFAULT_ID)
                .ratingScore(DEFAULT_RATING)
                .build();
    }

    public Rating getDefaultPassengerRating() {
        return Rating.builder()
                .id(DEFAULT_ID)
                .role(DEFAULT_PASSENGER_ROLE)
                .rideId(DEFAULT_ID)
                .uid(DEFAULT_ID)
                .ratingScore(DEFAULT_RATING)
                .build();
    }

    public RatingDto getDefaultRatingDto() {
        return RatingDto.builder()
                .role(DEFAULT_DRIVER_ROLE)
                .uid(DEFAULT_ID)
                .averageRating(DEFAULT_RATING)
                .build();
    }

    public RatingResponse getDefaultRatingResponse() {
        return RatingResponse.builder()
                .Rating(DEFAULT_INTEGER_RATING)
                .build();
    }

    public UpdateRatingRequest getDefaultUpdateRatingRequest() {
        return UpdateRatingRequest.builder()
                .rating(DEFAULT_RATING)
                .build();
    }

    public DelegationFromRidesRequest getDefaultDelegationFromRidesRequest() {
        return DelegationFromRidesRequest.builder()
                .rideId(DEFAULT_ID)
                .passId(DEFAULT_ID)
                .driverId(DEFAULT_ID)
                .build();
    }
}
