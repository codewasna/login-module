package com.sharenotebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharenotebook.dto.LoginDTO;
import com.sharenotebook.dto.ResponseDTO;
import com.sharenotebook.payload.LoginPayload;
import com.sharenotebook.service.LoginService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping
	public ResponseEntity<ResponseDTO<LoginDTO>> userLogin(@Valid @RequestBody LoginPayload payload) {
		log.info("Request arrived to User Login");
		LoginDTO response = loginService.userLogin(payload);
		return ResponseDTO.prepare("User authenticated", HttpStatus.OK.value(), response);
	}

}
