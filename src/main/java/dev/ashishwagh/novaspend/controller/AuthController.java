package dev.ashishwagh.novaspend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private  AuthService authService;
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignUpRequest request)
	{
		UserResponse userResponse=authService.signup(request);
		return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request)
	{
		UserResponse userResponse=authService.login(request);
		return ResponseEntity.ok(userResponse);
	}
}
