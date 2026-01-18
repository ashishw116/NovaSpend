package dev.ashishwagh.novaspend.dto;

import lombok.Data;

@Data
public class UserAuthResponse {
	private String jwt;
	private String id;
	private String refreshToken;
	private String name;
	private String email;
}
