package dev.ashishwagh.novaspend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class ChangePasswordRequest {
	@Pattern(
			  regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
			  message = "Password must be at least 8 characters, contain one letter, one number, and one special character"
			)

	@NotBlank(message = "Old Password is required")
    private String oldPassword;
	@Pattern(
			  regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
			  message = "Password must be at least 8 characters, contain one letter, one number, and one special character"
			)

	@NotBlank(message = "Password is required")
    private String newPassword;
	@NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
}
