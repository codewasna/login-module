package com.sharenotebook.service;

import com.sharenotebook.dto.RegisterDTO;
import com.sharenotebook.exception.CustomValidationException;
import com.sharenotebook.payload.RegisterPayload;

public interface RegistrationService {

	RegisterDTO create(RegisterPayload payload) throws CustomValidationException;

}
