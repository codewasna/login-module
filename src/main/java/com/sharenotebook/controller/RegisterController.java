package com.sharenotebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharenotebook.dto.RegisterDTO;
import com.sharenotebook.dto.ResponseDTO;
import com.sharenotebook.exception.CustomValidationException;
import com.sharenotebook.payload.RegisterPayload;
import com.sharenotebook.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<ResponseDTO<RegisterDTO>> userRegistration(@Valid @RequestBody RegisterPayload payload) throws CustomValidationException {
		RegisterDTO response = registrationService.create(payload);
		return ResponseDTO.prepare("User registered", HttpStatus.CREATED.value(), response);
	}
	
}
