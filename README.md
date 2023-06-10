# Ticket Booking App

Ticket Booking App is a web-based application that allows users to conveniently reserve movie tickets at a multiplex. With real-time seat availability and a user-friendly interface, users can easily select their preferred movie, screening time, and seats. The application provides information about screening rooms, available seats, and ticket prices. It supports multiple ticket types, such as adult, student, and child, with automatic calculation of the total amount to pay. Experience hassle-free movie ticket reservations with Ticket Booking App.

## Technologies Used

- Java 20
- Spring Boot 3.1.0
- Spring Boot Data JPA
- Spring Boot Web
- Spring Boot DevTools
- H2 Database
- Lombok
- Validation API
- Hibernate Validator
- Google Guava

## Getting Started

To get a local copy of the project up and running, follow these steps:

1. Clone the repository:

```
git clone https://github.com/your-username/ticket_booking_app.git
```

2. Navigate to the project directory:

```
cd ticket_booking_app
```

3. Build the project using Maven:

```
mvn clean install
```

4. Run the application:

```
mvn spring-boot:run
```

The application will start and be accessible at `http://localhost:8080`.

## Dependencies

The project uses the following dependencies:

- [Spring Boot Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- [Spring Boot Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [Spring Boot DevTools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
- [H2 Database](https://mvnrepository.com/artifact/com.h2database/h2)
- [Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
- [Spring Boot Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
- [Validation API](https://mvnrepository.com/artifact/javax.validation/validation-api)
- [Hibernate Validator](https://mvnrepository.com/artifact/org.hibernate.validator/hibernate-validator)
- [Google Guava](https://mvnrepository.com/artifact/com.google.guava/guava)

## Functionality

The Ticket Booking App provides the following functionality through its methods:

1. `GET /cinema/dates` - Retrieves a list of available date times.
   - Returns a list of `DateTimeDto` objects containing information about available date times for screenings.

2. `GET /cinema/movies` - Retrieves a list of movies for a given interval.
   - Accepts a request payload of type `IntervalDto` specifying the interval.
   - Returns a list of `MovieTimeDto` objects containing information about movies scheduled within the specified interval.

3. `GET /cinema/screening/{IdScreening}` - Retrieves details of a specific screening.
   - Accepts the screening ID as a path variable.
   - Returns a `ScreeningDto` object containing detailed information about the screening, including hall and seat details.

4. `GET /cinema/screening/{IdScreening}/seats` - Retrieves available seats for a specific screening.
   - Accepts the screening ID as a path variable.
   - Returns a list of `SeatDto` objects containing information about available seats for the screening.

5. `PUT /cinema/screening/reservation/{IdScreening}` - Adds a reservation for a specific screening.
   - Accepts a request payload of type `AddReservationDto` containing reservation details.
   - Accepts the

screening ID as a path variable.
- Returns a success message with the reservation ID if the reservation is added successfully.
- Returns an error message with the appropriate status code if the reservation is invalid or cannot be added.

6. `GET /cinema/screening/reservation/{IdScreening}` - Retrieves information about a reservation for a specific screening.
   - Accepts the screening ID as a path variable.
   - Returns a `ReservationDto` object containing information about the reservation.
   - Returns an error message with the appropriate status code if the reservation is invalid or not found.

## Building the Project

The project is built using Maven. To build the project, run the following command:

```
mvn clean install
```

The command will compile the source code and package the application into a JAR file.

## Running Tests

**Note:** The tests for the project are not yet implemented.

To run the tests for the project, use the following command:

```
mvn test
```

The tests will be executed, and the results will be displayed in the console.

## License

This project is licensed under the [MIT License](LICENSE).
