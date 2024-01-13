package com.example.ratingservice.service;

import com.example.ratingservice.dao.RatingDAO;
import com.example.ratingservice.dto.RatingDTO;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingDAO ratingDAO;

    public ResponseEntity<Rating> saveRating(Rating rating) {
        ratingDAO.save(rating);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }


    public ResponseEntity<RatingDTO> getAverage(Role role, int id) {
        Optional<List<Rating>> dbResult = ratingDAO.findByRoleAndUid(role, id);
        //TODO make UserNotFoundException
        List<Rating> ratings = dbResult.get();
        Float sum = 0F;
        for(Rating rating: ratings) {
            sum += rating.getRatingScore();
        }
        return new ResponseEntity<>(new RatingDTO(role, id,sum/ratings.size()), HttpStatus.OK);
    }

    public ResponseEntity<List<Rating>> getRating(Role role, int id) {
        Optional<List<Rating>> dbResult = ratingDAO.findByRoleAndUid(role, id);
        //TODO make UserNotFoundException
        List<Rating> ratings = dbResult.get();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}
