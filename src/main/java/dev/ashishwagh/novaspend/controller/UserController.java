package dev.ashishwagh.novaspend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ashishwagh.novaspend.dto.ChangePasswordRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.response.ApiResponse;
import dev.ashishwagh.novaspend.service.UserService;
import dev.ashishwagh.novaspend.utility.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final SecurityUtil util;
	@GetMapping
	public ResponseEntity<ApiResponse<UserResponse>> getUserProfile(Authentication authentication)
	{
		String userId=util.getUserId(authentication);
		return ResponseEntity.ok(ApiResponse.success("User Profile Fetched",userService.getUserDetails(userId)));
	}
	@PatchMapping("/password")
	public ResponseEntity<ApiResponse<UserResponse>> changeUserPassword(@Valid @RequestBody ChangePasswordRequest passwordRequest,Authentication authentication)
	{
		String userId=util.getUserId(authentication);
		return ResponseEntity.ok(ApiResponse.success("Password Change Successfully", userService.changeUserPassword(userId, passwordRequest.getOldPassword(), passwordRequest.getNewPassword(), passwordRequest.getConfirmPassword())));
	}
	@DeleteMapping
	public ResponseEntity<ApiResponse<String>> deleteProfile(Authentication authentication)
	{
		String userId=util.getUserId(authentication);
		userService.deleteUser(userId);
		return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
	}
	
}
