package dev.ashishwagh.novaspend.mapper;

import org.springframework.stereotype.Component;

import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserAuthResponse;
import dev.ashishwagh.novaspend.model.User;
@Component
public class UserAuthMapper {
	public User toUser(SignUpRequest request)
	{
		User user=new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		return user;
	}
	
	public UserAuthResponse toUserResponse(User user)
	{
		UserAuthResponse userRes=new UserAuthResponse();
		userRes.setId(user.getId());
		userRes.setEmail(user.getEmail());
		userRes.setName(user.getName());
		return userRes;
	}
}
