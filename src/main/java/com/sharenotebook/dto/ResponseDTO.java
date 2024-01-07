package com.sharenotebook.dto;

import org.springframework.http.ResponseEntity;

import lombok.Data;

@Data
public class ResponseDTO<T> {

	private String message;
	private Integer httpStatusCode;
	private T body;
	
	public ResponseDTO(String message, Integer httpStatusCode, T body) {
		super();
		this.message = message;
		this.httpStatusCode = httpStatusCode;
		this.body = body;
	}
	
	public static <T> ResponseEntity<ResponseDTO<T>> prepare(String message, Integer httpStatusCode, T body) {
		return ResponseEntity.ofNullable(new ResponseDTO<T>(message, httpStatusCode, body));
	}
	
}
