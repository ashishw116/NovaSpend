package dev.ashishwagh.novaspend.dto;

import lombok.Data;

@Data
public class UserResponse {
	private String jwt;
	private String id;
	private String name;
	private String email;
}
