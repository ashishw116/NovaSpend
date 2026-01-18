package dev.ashishwagh.novaspend.mapper;

import org.springframework.stereotype.Component;

import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.model.User;
@Component
public class UserMapper {
	public UserResponse toUserResponse(User user) {
	    UserResponse dto = new UserResponse();
	    dto.setId(user.getId());
	    dto.setName(user.getName());
	    dto.setEmail(user.getEmail());
	    dto.setRole(user.getRole());
	    dto.setStatus(user.getStatus());
	    dto.setCreatedAt(user.getCreatedAt());
	    return dto;
	}

}
