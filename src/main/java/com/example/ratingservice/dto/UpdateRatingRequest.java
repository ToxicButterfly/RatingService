package com.example.ratingservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRatingRequest {
    private Integer uId;
    private Float rating;
}
