package dev.ashishwagh.novaspend.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
	@Id
	private String id;
	private String token;
	private Instant expiryDate;
	private String userId;
}
