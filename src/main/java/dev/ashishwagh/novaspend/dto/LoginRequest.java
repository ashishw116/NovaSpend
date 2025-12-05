package dev.ashishwagh.novaspend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid Email Format")
	private String email;
	@NotBlank(message = "Password is required")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message="Password must contain only letters and numbers")
	private String password;
}
