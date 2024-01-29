package com.example.ratingservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingRequest {
    private Float rating;
}
