package com.blinked.modules.core.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.blinked.modules.core.response.ApiError;
import com.blinked.modules.core.response.Responses;
import com.blinked.modules.core.response.ValidationError;

@RestControllerAdvice
public class GlobalErrorHandler {
	@ResponseStatus(code = BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Collection<ValidationError> badRequest(MethodArgumentNotValidException exception) {
		return ValidationError.of(exception);
	}

	@ResponseStatus(code = BAD_REQUEST)
	@ExceptionHandler(BadRequestException.class)
	public ApiError badRequest(BadRequestException exception) {
		return new ApiError(exception.getMessage(), UNAUTHORIZED);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ApiError> status(ResponseStatusException exception) {
		return Responses.fromException(exception);
	}

	@ResponseStatus(code = UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ApiError unauthorized(AccessDeniedException exception) {
		return new ApiError("Not authorized.", UNAUTHORIZED);
	}
}