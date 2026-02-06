package dev.ashishwagh.novaspend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.RefreshTokenRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserAuthResponse;
import dev.ashishwagh.novaspend.response.ApiResponse;
import dev.ashishwagh.novaspend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;     

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserAuthResponse>> signup(@Valid @RequestBody SignUpRequest request)
	{
		UserAuthResponse userResponse=authService.signup(request);
		return new ResponseEntity<>(ApiResponse.success("SignUp Successfully",userResponse),HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<UserAuthResponse>> login(@Valid @RequestBody LoginRequest request)
	{
		UserAuthResponse userResponse=authService.login(request);
		return ResponseEntity.ok(ApiResponse.success("Login Successfully",userResponse));
	}
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<String>> logout(@RequestBody RefreshTokenRequest request)
	{
		authService.logout(request.getRefreshToken());
		return ResponseEntity.ok(ApiResponse.success("Logout Successfully"));
	}
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<UserAuthResponse>> refreshToken(@RequestBody RefreshTokenRequest request)
	{
		UserAuthResponse userResponse=authService.refreshAccessToken(request.getRefreshToken());
		return ResponseEntity.ok(ApiResponse.success("Token refreshed Successfully",userResponse));
	}
}
