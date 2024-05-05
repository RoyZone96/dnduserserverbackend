package com.dndbackendlayer.dndbackend.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.HashMap;


@ControllerAdvice
public class UserNotFound {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
     public Map<String, String> exceptionHandler(UserNotFoundException ex) {
         Map<String, String> response = new HashMap<>();
         response.put("message", ex.getMessage());
         return response;
     }
}