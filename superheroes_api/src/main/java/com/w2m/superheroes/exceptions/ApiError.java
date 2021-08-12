package com.w2m.superheroes.exceptions;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ApiError {
	
    /** status. */
    private HttpStatus status;
    
    /** message. */
    private String message;
    
    /** errors. */
    private List<String> errors;
    
    /** stack trace. */
    private StackTraceElement[] stackTrace;
    
    
    public ApiError(HttpStatus status, String message, List<String> errors) {
	super();
	this.status = status;
	this.message = message;
	this.errors = errors;
    }
    
    public ApiError(HttpStatus status, String message, String error) {
	super();
	this.status = status;
	this.message = message;
	errors = Arrays.asList(error);
    }

}
