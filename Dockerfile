FROM openjdk:21
ENV PORT 8083
EXPOSE 8083
ADD /target/RatingService-0.0.1-SNAPSHOT.jar rating-service.jar
ENTRYPOINT ["java", "-jar", "rating-service.jar"]