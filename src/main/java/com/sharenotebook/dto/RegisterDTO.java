package com.sharenotebook.dto;

import java.util.Set;

import com.sharenotebook.enums.RoleEnum;

import lombok.Data;

@Data
public class RegisterDTO {

	private Long id;
	private String username;
	private Set<RoleEnum> roles;
	
}
