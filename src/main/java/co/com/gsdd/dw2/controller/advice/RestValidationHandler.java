package co.com.gsdd.dw2.controller.advice;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.gsdd.dw2.exception.AttackTypeNotFoundException;

@ControllerAdvice
public class RestValidationHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConflict(ConstraintViolationException e) {
		StringBuilder messages = new StringBuilder();
		e.getConstraintViolations().forEach(cv -> messages.append(cv.getMessage()).append(System.lineSeparator()));
		return ResponseEntity.badRequest().body(messages.toString());
	}

	@ExceptionHandler(AttackTypeNotFoundException.class)
	protected ResponseEntity<Object> handleAttackTypeNotFound(AttackTypeNotFoundException e) {
		return ResponseEntity.badRequest().body("AttackType id is not currently defined, please define and try again");
	}

}
