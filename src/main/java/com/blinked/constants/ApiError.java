package com.blinked.constants;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApiError {
	@Schema(example = "generic error message", required = true)
	private final String message;

	@Schema(example = "999", required = true)
	private final Integer status;

	@Schema(required = false)
	public final Collection<ValidationError> errors;

	public ApiError(String message, Integer status) {
		this.message = message;
		this.status = status;
		this.errors = new ArrayList<ValidationError>();
	}

	public ApiError(String message, HttpStatus status) {
		this.message = message;
		this.status = status.value();
		this.errors = new ArrayList<ValidationError>();
	}

	public ApiError(HttpStatus status, Collection<ValidationError> errors) {
		this.message = "Check the 'errors' property for more details.";
		this.status = status.value();
		this.errors = errors;
	}

	public ApiError(HttpStatus status, String error) {
		this.message = "Check the 'errors' property for more details.";
		this.status = status.value();
		this.errors = new ArrayList<ValidationError>();
		this.errors.add(new ValidationError(error));
	}

	public ApiError(HttpStatus status, ValidationError error) {
		this.message = "Check the 'errors' property for more details.";
		this.status = status.value();
		this.errors = new ArrayList<ValidationError>();
		this.errors.add(error);
	}
}