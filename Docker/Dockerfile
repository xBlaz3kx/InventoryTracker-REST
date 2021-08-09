# Prepare the files for gradle
FROM gradle:6.6.1-jdk14 AS gradle-src
COPY ../src/ /home/gradle/src
WORKDIR /home/gradle/src

# Run gradle tests
FROM gradle-src AS gradle-test
WORKDIR /home/gradle/src
ENTRYPOINT []
RUN chmod u+x ./gradlew
CMD ["./gradlew", "test"]

# BUILD with gradle
FROM gradle-src AS gradle-build
RUN gradle build -x test --no-daemon

# Boot the application
FROM openjdk:14-jdk as app-run
EXPOSE 8080
RUN mkdir /app
COPY --from=gradle-build /home/gradle/src/build/libs/*.jar /app/inventory_tracker.jar
ENTRYPOINT ["java","-jar","/app/inventory_tracker.jar"]