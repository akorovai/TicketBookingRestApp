package com.akorovai.springboot.ticket_booking_app.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoMoviesFoundException extends RuntimeException{
    public NoMoviesFoundException(String message) {
        super(message);
    }

}




