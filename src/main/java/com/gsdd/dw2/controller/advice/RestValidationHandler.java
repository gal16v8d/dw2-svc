package com.gsdd.dw2.controller.advice;

import com.gsdd.dw2.model.response.ApiError;
import java.util.Arrays;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestValidationHandler extends ResponseEntityExceptionHandler {

  private static final String UNIQUE_INDEX_OR_PRIMARY_KEY_VIOLATION =
      "Unique index or primary key violation";

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ApiError> handleConflict(ConstraintViolationException e) {
    StringBuilder messages = new StringBuilder();
    e.getConstraintViolations()
        .forEach(cv -> messages.append(cv.getMessage()).append(System.lineSeparator()));
    return ResponseEntity.badRequest().body(new ApiError(messages.toString()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ApiError> handleConflict(Exception e) {
    boolean indexViolated =
        Arrays.toString(e.getStackTrace()).contains(UNIQUE_INDEX_OR_PRIMARY_KEY_VIOLATION);
    if (indexViolated) {
      return ResponseEntity.badRequest().body(new ApiError(UNIQUE_INDEX_OR_PRIMARY_KEY_VIOLATION));
    } else {
      return ResponseEntity.internalServerError().body(new ApiError(e.getLocalizedMessage()));
    }
  }
}
