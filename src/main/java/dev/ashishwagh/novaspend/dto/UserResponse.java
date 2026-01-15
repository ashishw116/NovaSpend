package dev.ashishwagh.novaspend.dto;

import java.time.LocalDateTime;

import dev.ashishwagh.novaspend.model.Roles;
import dev.ashishwagh.novaspend.model.Status;
import lombok.Data;

@Data
public class UserResponse {
	private String id;
	private String name;
	private String email;
	private Roles role;
	private Status status;
	private LocalDateTime createdAt;
}
