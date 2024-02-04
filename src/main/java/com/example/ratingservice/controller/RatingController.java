package com.example.ratingservice.controller;

import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.dto.RatingsDto;
import com.example.ratingservice.exception.RatingNotFoundException;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import com.example.ratingservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.saveRating(rating));
    }

    @GetMapping("{role}/{id}/all")
    public ResponseEntity<RatingsDto> getRating(@PathVariable Role role, @PathVariable int id) throws RatingNotFoundException {
        return ResponseEntity.ok(ratingService.getRating(role, id));
    }

    @GetMapping("{role}/{id}")
    public ResponseEntity<RatingDto> getAverage(@PathVariable Role role, @PathVariable int id) throws RatingNotFoundException {
        return ResponseEntity.ok(ratingService.getAverage(role, id));
    }

}
