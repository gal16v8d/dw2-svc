package com.gsdd.dw2.controller.advice;

import com.gsdd.dw2.model.response.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestValidationHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<ApiError> handleIntegrityException(Exception e) {
    return ResponseEntity.status(HttpStatus.CONFLICT.value())
        .body(ApiError.builder().message(e.getLocalizedMessage()).build());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiError> handleException(Exception e) {
    return ResponseEntity.internalServerError()
        .body(ApiError.builder().message(e.getLocalizedMessage()).build());
  }

}
