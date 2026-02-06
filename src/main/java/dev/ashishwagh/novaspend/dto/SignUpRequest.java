package dev.ashishwagh.novaspend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
	@Size(min=3,max=30, message = "Name Have Min 3 And Max 30 Chars")
	@Pattern(regexp="^[A-Za-z ]+$",message = "Name Only Have Alphabets")
	@NotBlank(message = "Name is required")
	private String name;
	@Email(message = "Invalid name format")
	@NotBlank(message = "Email is required")
	private String email;
	@Pattern(
			  regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
			  message = "Password must be at least 8 characters, contain one letter, one number, and one special character"
			)

	@NotBlank(message = "Password is required")
	private String password;
}
