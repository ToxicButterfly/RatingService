Feature: Rating Service
  Scenario: Saving rating data
    Given Rating expected to be saved
    When Create request passed to saveRating method
    Then The response should return rating data

  Scenario: Getting average rating of driver user
    Given Database contains records of user with "Driver" role and id 1 ratings
    When Get request of "Driver" role with id 1 passed to getAverage method
    Then The response should return "Driver" role ratingDto data

  Scenario: Getting average rating of passenger user
    Given Database contains records of user with "Passenger" role and id 1 ratings
    When Get request of "Passenger" role with id 1 passed to getAverage method
    Then The response should return "Passenger" role ratingDto data

  Scenario: Getting rating of driver user
    Given Database contains records of user with "Driver" role and id 1 ratings
    When Get request of "Driver" role with id 1 passed to getRating method
    Then The response should return "Driver" role ratingDto data

  Scenario: Getting rating of passenger user
    Given Database contains records of user with "Passenger" role and id 1 ratings
    When Get request of "Passenger" role with id 1 passed to getRating method
    Then The response should return "Passenger" role ratingDto data
