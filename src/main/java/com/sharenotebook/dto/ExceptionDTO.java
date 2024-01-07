package com.sharenotebook.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDTO {

	private String message;
	private Integer httpStatusCode;
	private LocalDateTime timestamp;
	private String exception;
	
}
