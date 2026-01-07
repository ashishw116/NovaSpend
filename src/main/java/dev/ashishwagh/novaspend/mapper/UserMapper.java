package dev.ashishwagh.novaspend.mapper;

import org.springframework.stereotype.Component;

import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.model.User;
@Component
public class UserMapper {
	public User toUser(SignUpRequest request)
	{
		User user=new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		return user;
	}
	
	public UserResponse toUserResponse(User user)
	{
		UserResponse userRes=new UserResponse();
		userRes.setId(user.getId());
		userRes.setEmail(user.getEmail());
		userRes.setName(user.getName());
		return userRes;
	}
}
