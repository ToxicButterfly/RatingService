package com.example.ratingservice.service;

import com.example.ratingservice.dto.DelegationFromRidesRequest;
import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.dto.RatingsDto;
import com.example.ratingservice.exception.RatingNotFoundException;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;

public interface RatingService {

    Rating saveRating(Rating rating);
    RatingDto getAverage(Role role, int id) throws RatingNotFoundException;
    RatingsDto getRating(Role role, int id) throws RatingNotFoundException;
    void updateRating(DelegationFromRidesRequest request);
}
