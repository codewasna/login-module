package com.sharenotebook.service;

import com.sharenotebook.dto.LoginDTO;
import com.sharenotebook.payload.LoginPayload;

public interface LoginService {

	LoginDTO userLogin(LoginPayload payload);

}
