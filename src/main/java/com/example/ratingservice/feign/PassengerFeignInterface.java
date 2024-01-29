package com.example.ratingservice.feign;

import com.example.ratingservice.dto.RatingResponse;
import com.example.ratingservice.dto.UpdateRatingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PASSENGER-SERVICE")
public interface PassengerFeignInterface {

    @GetMapping("api/v1/passenger/{id}/rating")
    public ResponseEntity<RatingResponse> askOpinion(@PathVariable int id);

    @PutMapping("api/v1/passenger/{id}/rating")
    public void updateRating(@RequestBody UpdateRatingRequest request);

}
