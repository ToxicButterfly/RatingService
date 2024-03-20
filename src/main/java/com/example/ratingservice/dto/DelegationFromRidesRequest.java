package com.example.ratingservice.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DelegationFromRidesRequest {
    private Integer rideId;
    private Integer passId;
    private Integer driverId;
    private String token;
}
