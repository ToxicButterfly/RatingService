package com.example.ratingservice.dto;

import com.example.ratingservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingDto {

    private Role role;
    private Integer uid;
    private Float averageRating;
}
