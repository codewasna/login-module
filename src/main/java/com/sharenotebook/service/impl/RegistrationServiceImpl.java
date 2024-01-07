package com.sharenotebook.service.impl;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sharenotebook.dto.RegisterDTO;
import com.sharenotebook.entity.UserEntity;
import com.sharenotebook.enums.RoleEnum;
import com.sharenotebook.exception.CustomValidationException;
import com.sharenotebook.payload.RegisterPayload;
import com.sharenotebook.repository.UserRepository;
import com.sharenotebook.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public RegisterDTO create(RegisterPayload payload) throws CustomValidationException {

		if (!payload.getPassword().equals(payload.getConfirmPassword())) {
			throw new CustomValidationException("Password and confirm password didn't match.");
		}
		
		payload.setPassword(passwordEncoder.encode(payload.getPassword()));

		UserEntity user = modelMapper.map(payload, UserEntity.class);
		user.setRoles(Set.of(RoleEnum.ROLE_USER));
		return modelMapper.map(userRepository.save(user), RegisterDTO.class);

	}

}
