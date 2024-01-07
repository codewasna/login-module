package com.sharenotebook.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sharenotebook.dto.ExceptionDTO;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler({ CustomValidationException.class, MethodArgumentNotValidException.class })
	public ResponseEntity<ExceptionDTO> customValidationException(Exception exception) {
		return prepareExcpetionResponse(exception, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ AccessDeniedException.class, AuthenticationException.class, ExpiredJwtException.class, SignatureException.class })
	public ResponseEntity<ExceptionDTO> authenticationException(Exception exception) {
		return prepareExcpetionResponse(exception, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDTO> exception(Exception exception) {
		return prepareExcpetionResponse(exception, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<ExceptionDTO> prepareExcpetionResponse(Exception exception, HttpStatus httpStatus) {
		ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getLocalizedMessage(), httpStatus.value(),
				LocalDateTime.now(), exception.getClass().getSimpleName());
		return ResponseEntity.status(httpStatus).body(exceptionDTO);
	}

}
