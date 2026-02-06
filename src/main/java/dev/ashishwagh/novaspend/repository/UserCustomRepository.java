package dev.ashishwagh.novaspend.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.model.Status;

public interface UserCustomRepository {
	Page<UserResponse> filterUsers(int page,int size,Status status,LocalDateTime fromDate,LocalDateTime toDate);
}
