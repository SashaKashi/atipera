package com.task.demo.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<Object> handle(UsernameNotFoundException e, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(e.getStatus().value(), e.getMessage());
    return handleExceptionInternal(e, errorResponse, new HttpHeaders(), e.getStatus(), request);
  }
}