package dev.ashishwagh.novaspend.service;

import java.util.List;

import dev.ashishwagh.novaspend.dto.UserResponse;

public interface AdminService {
	public List<UserResponse> getUsers();
	public UserResponse getUserById(String id);
	UserResponse blockUser(String id);
	UserResponse deleteUser(String id);
	UserResponse reactiveUser(String id);
}
