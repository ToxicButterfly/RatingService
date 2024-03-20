package com.example.ratingservice.feign;

import com.example.ratingservice.dto.RatingResponse;
import com.example.ratingservice.dto.UpdateRatingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "${feign.client.driver.name}", path = "${feign.client.driver.path}", url = "${feign.client.driver.url}")
public interface DriverFeignInterface {

    @GetMapping("{id}/rating")
    public ResponseEntity<RatingResponse> askOpinion(@PathVariable int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @PutMapping("{id}/rating")
    public void updateRating(@RequestBody UpdateRatingRequest request, @PathVariable Integer id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);
}
