package dev.ashishwagh.novaspend.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.ashishwagh.novaspend.dto.PageResponse;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.response.ApiResponse;
import dev.ashishwagh.novaspend.service.AdminService;
import lombok.RequiredArgsConstructor;
import dev.ashishwagh.novaspend.model.Status;
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	private final AdminService adminService;
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getUsers(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "50") int size,@RequestParam(required = false) Status status,@RequestParam(required = false)LocalDateTime fromDate,@RequestParam(required = false)LocalDateTime toDate)
	{
		return ResponseEntity.ok(ApiResponse.success("Users fetched successfully", adminService.getUsers(page,size,status,fromDate,toDate)));
	}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id)
	{
		UserResponse users=adminService.getUserById(id);
		return ResponseEntity.ok(ApiResponse.success("User fetched successfully", users));
	}
	@PutMapping("/{id}/block")
	public ResponseEntity<ApiResponse<UserResponse>> blockUser(@PathVariable String id)
	{
		UserResponse users=adminService.blockUser(id);
		return ResponseEntity.ok(ApiResponse.success("User blocked successfully", users));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable String id)
	{
		UserResponse users=adminService.deleteUser(id);
		return ResponseEntity.ok(ApiResponse.success("User deleted successfully", users));
	}
	@PutMapping("/{id}/activate")
	public ResponseEntity<ApiResponse<UserResponse>> reactiveUser(@PathVariable String id)
	{
		UserResponse users=adminService.reactiveUser(id);
		return ResponseEntity.ok(ApiResponse.success("User Activated successfully", users));
	}
}
