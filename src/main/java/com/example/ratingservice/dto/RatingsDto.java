package com.example.ratingservice.dto;

import com.example.ratingservice.model.Rating;

import java.util.List;

public record RatingsDto(List<Rating> ratings) {
}
