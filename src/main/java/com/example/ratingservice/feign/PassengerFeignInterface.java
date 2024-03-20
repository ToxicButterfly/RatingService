package com.example.ratingservice.feign;

import com.example.ratingservice.dto.RatingResponse;
import com.example.ratingservice.dto.UpdateRatingRequest;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.passenger.name}", path = "${feign.client.passenger.path}", url = "${feign.client.passenger.url}")
public interface PassengerFeignInterface {

    @GetMapping("{id}/rating")
    public ResponseEntity<RatingResponse> askOpinion(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @PutMapping("{id}/rating")
    public void updateRating(@RequestBody UpdateRatingRequest request, @PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

}
