package com.chardaydevs.job_hunt_dev.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException{

    public InvalidIdException(String message) {
        super(message);
    }

}
