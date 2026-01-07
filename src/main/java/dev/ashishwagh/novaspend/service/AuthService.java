package dev.ashishwagh.novaspend.service;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;

public interface AuthService {
	UserResponse signup(SignUpRequest request);
	UserResponse login(LoginRequest request);
	
}
