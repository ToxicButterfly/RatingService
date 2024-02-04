package com.example.ratingservice.dto;

import com.example.ratingservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
    private Role role;
    private Integer uid;
    private Float averageRating;

}
