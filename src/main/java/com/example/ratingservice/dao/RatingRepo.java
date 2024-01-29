package com.example.ratingservice.dao;

import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepo extends JpaRepository<Rating, Integer> {
    Optional<List<Rating>> findByRoleAndUid(Role role, Integer uid);
}
