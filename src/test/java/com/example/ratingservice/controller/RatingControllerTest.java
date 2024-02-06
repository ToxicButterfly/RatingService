package com.example.ratingservice.controller;

import com.example.ratingservice.dto.RatingDto;
import com.example.ratingservice.model.Rating;
import com.example.ratingservice.repo.RatingRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.example.ratingservice.util.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RatingRepo ratingRepo;

    @Test
    void saveRating_shouldReturnRating() throws Exception {
        doReturn(getDefaultRating()).when(ratingRepo).save(any(Rating.class));

        mockMvc.perform(post("/api/v1/ratings").content("{\n" +
                "    \"id\": 1,\n" +
                "    \"role\": \"Passenger\",\n" +
                "    \"rideId\": 1,\n" +
                "    \"uid\": 1,\n" +
                "    \"ratingScore\": 5.0\n" +
                "}").contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1," +
                        "\"role\":\"Passenger\"," +
                        "\"rideId\":1," +
                        "\"uid\":1," +
                        "\"ratingScore\":5.0}"));
    }

    @Test
    void getRating_shouldReturnDriverRatingsDto_whenRequestDriverRating() throws Exception {
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultRating(), getDefaultRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        mockMvc.perform(get("/api/v1/ratings/Driver/1/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"ratings\":[" +
                        "{\"id\":1," +
                        "\"role\":\"Driver\"," +
                        "\"rideId\":1," +
                        "\"uid\":1," +
                        "\"ratingScore\":5.0}," +
                        "{\"id\":1," +
                        "\"role\":\"Driver\"," +
                        "\"rideId\":1," +
                        "\"uid\":1," +
                        "\"ratingScore\":5.0}]}\n"));
    }

    @Test
    void getRating_shouldReturnPassengerRatingsDto_whenRequestPassengerRating() throws Exception {
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultPassengerRating(), getDefaultPassengerRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);

        mockMvc.perform(get("/api/v1/ratings/Passenger/1/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"ratings\":[" +
                        "{\"id\":1," +
                        "\"role\":\"Passenger\"," +
                        "\"rideId\":1," +
                        "\"uid\":1," +
                        "\"ratingScore\":5.0}," +
                        "{\"id\":1," +
                        "\"role\":\"Passenger\"," +
                        "\"rideId\":1," +
                        "\"uid\":1," +
                        "\"ratingScore\":5.0}]}\n"));
    }

    @Test
    void getAverage_shouldReturnDriverRatingDto_whenRequestDriverAverage() throws Exception {
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultRating(), getDefaultRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_DRIVER_ROLE, DEFAULT_ID);

        mockMvc.perform(get("/api/v1/ratings/Driver/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"role\":\"Driver\"," +
                        "\"uid\":1," +
                        "\"averageRating\":5.0}"));
    }

    @Test
    void getAverage_shouldReturnPassengerRatingDto_whenRequestPassengerAverage() throws Exception {
        ArrayList<Rating> list = new ArrayList<>(Arrays.asList(getDefaultPassengerRating(), getDefaultPassengerRating()));
        doReturn(Optional.of(list)).when(ratingRepo).findByRoleAndUid(DEFAULT_PASSENGER_ROLE, DEFAULT_ID);

        mockMvc.perform(get("/api/v1/ratings/Passenger/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"role\":\"Passenger\"," +
                        "\"uid\":1," +
                        "\"averageRating\":5.0}"));
    }
}
