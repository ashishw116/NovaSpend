package dev.ashishwagh.novaspend.service;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserAuthResponse;

public interface AuthService {
	UserAuthResponse signup(SignUpRequest request);
	UserAuthResponse login(LoginRequest request);
	UserAuthResponse refreshAccessToken(String refreshToken);
	void logout(String refreshToken);
}
