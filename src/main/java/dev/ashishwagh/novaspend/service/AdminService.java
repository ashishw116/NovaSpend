package dev.ashishwagh.novaspend.service;

import java.time.LocalDateTime;

import dev.ashishwagh.novaspend.dto.PageResponse;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.model.Status;

public interface AdminService {
	public PageResponse<UserResponse> getUsers(int page,int size,Status status,LocalDateTime fromDate,LocalDateTime toDate);
	public UserResponse getUserById(String id);
	UserResponse blockUser(String id);
	UserResponse deleteUser(String id);
	UserResponse reactiveUser(String id);
}
