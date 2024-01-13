package com.example.ratingservice.controller;

import com.example.ratingservice.dto.RatingDTO;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating) {
        return ratingService.saveRating(rating);
    }

    @GetMapping("{role}/{id}/all")
    public ResponseEntity<List<Rating>> getRating(@PathVariable Roles role,@PathVariable int id) {
        return ratingService.getRating(role, id);
    }

    @GetMapping("{role}/{id}")
    public ResponseEntity<RatingDTO> getAverage(@PathVariable Roles role, @PathVariable int id) {
        return ratingService.getAverage(role, id);
    }

    @GetMapping("evaluate")
    public ResponseEntity<List<Rating>> evaluate() {
        ratingService.evaluate();
    }
}
