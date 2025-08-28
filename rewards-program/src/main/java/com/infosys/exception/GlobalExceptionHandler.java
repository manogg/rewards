package com.infosys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
		log.error("Bad request: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError("BAD_REQUEST", ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGeneric(Exception ex) {
		log.error("Internal server error", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(buildError("INTERNAL_ERROR", "Something went wrong. Please try again later."));
	}

	private ErrorResponse buildError(String code, String message) {
		return new ErrorResponse(code, message);
	}

	static class ErrorResponse {
		private String errorCode;
		private String message;

		public ErrorResponse(String errorCode, String message) {
			this.errorCode = errorCode;
			this.message = message;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getMessage() {
			return message;
		}
	}

}
