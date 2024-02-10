package com.example.ratingservice.component;

import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.dto.RatingsDto;
import com.example.ratingservice.feign.DriverFeignInterface;
import com.example.ratingservice.feign.PassengerFeignInterface;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.model.Role;
import com.example.ratingservice.repo.RatingRepo;
import com.example.ratingservice.service.impl.RatingServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.ratingservice.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class RatingComponentTest {

    @Mock
    private RatingRepo ratingRepo;
    @Mock
    private PassengerFeignInterface passengerFeignInterface;
    @Mock
    private DriverFeignInterface driverFeignInterface;
    @InjectMocks
    private RatingServiceImpl ratingService;
    private Exception exception;
    private Rating ratingResponse;
    private RatingDto ratingDtoResponse;

    @Given("Rating expected to be saved")
    public void ratingExpectedToBeSaved() {
        doReturn(getDefaultRating()).when(ratingRepo).save(any(Rating.class));
    }

    @When("Create request passed to saveRating method")
    public void createRequestPassedToSaveRatingMethod() {
        try {
            ratingResponse = ratingService.saveRating(getDefaultRating());
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should return rating data")
    public void theResponseShouldReturnRatingData() {
        assertEquals(getDefaultRating(), ratingResponse);
    }

    @Given("Database contains records of user with {string} role and id {int} ratings")
    public void databaseContainsRecordsOfUsersRatings(String role, int id) {
        Rating rating = Rating.builder()
                .id(id)
                .ratingScore(DEFAULT_RATING)
                .role(Role.valueOf(role))
                .build();
        List<Rating> ratings = new ArrayList<>(Arrays.asList(rating, rating, rating));
        doReturn(Optional.of(ratings)).when(ratingRepo).findByRoleAndUid(Role.valueOf(role), id);
    }

    @When("Get request of {string} role with id {int} passed to getAverage method")
    public void getRequestOfWithIdPassedToGetAverageMethod(String role, int id) {
        try {
            ratingDtoResponse = ratingService.getAverage(Role.valueOf(role), id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should return {string} role ratingDto data")
    public void theResponseShouldReturnRatingDtoData(String role) {
        RatingDto ratingDto = RatingDto.builder()
                .uid(DEFAULT_ID)
                .averageRating(DEFAULT_RATING)
                .role(Role.valueOf(role))
                .build();
        assertEquals(ratingDto, ratingDtoResponse);
    }

    @When("Get request of {string} role with id {int} passed to getRating method")
    public void getRequestOfRoleWithIdPassedToGetRatingMethod(String role, int id) {
        try {
            ratingDtoResponse = ratingService.getAverage(Role.valueOf(role), id);
        } catch (Exception e) {
            exception = e;
        }
    }
}
