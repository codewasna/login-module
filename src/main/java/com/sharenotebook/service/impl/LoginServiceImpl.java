package com.sharenotebook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.sharenotebook.configuration.security.JwtUtil;
import com.sharenotebook.dto.LoginDTO;
import com.sharenotebook.payload.LoginPayload;
import com.sharenotebook.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public LoginDTO userLogin(LoginPayload payload) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
		log.info("User authentication passed");
		String username = authentication.getName();
		var authorities = authentication.getAuthorities();
		User user = new User(username, "", authorities);
		String token = jwtUtil.createToken(user);
		return new LoginDTO(username, token);
	}

}
