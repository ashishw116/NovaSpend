package dev.ashishwagh.novaspend.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.mapper.UserMapper;
import dev.ashishwagh.novaspend.model.Status;
import dev.ashishwagh.novaspend.model.User;
import dev.ashishwagh.novaspend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RedisService redisService;
	private final RefreshTokenService refreshTokenService;
	@Override
	public UserResponse getUserDetails(String userId)
	{
		String key="user:profile:"+userId;
		UserResponse cached=redisService.get(key,new TypeReference<UserResponse>(){});
		if(cached!=null)
		{
			return cached;
		}
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found!!"));
		UserResponse response=userMapper.toUserResponse(user);
		redisService.set(key, response, (long) 15);
		return response;
	}
	
	@Override
	public UserResponse changeUserPassword(String userId,String oldPassword,String newPassword,String confirmPassword)
	{
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found!!"));
		if(!passwordEncoder.matches(oldPassword, user.getPassword()))
		{
			throw new BadCredentialsException("Wrong Credentials");
		}
		if(!newPassword.equals(confirmPassword))
		{
			throw new BadCredentialsException("Password do not match");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		User saved=userRepository.save(user);
		redisService.delete("user:profile:"+userId);
		refreshTokenService.deleteByUserId(userId);
		return userMapper.toUserResponse(saved);
	}

	@Override
	public void deleteUser(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found!!"));
		user.setStatus(Status.DELETED);
		userRepository.save(user);
		redisService.delete("user:profile:"+userId);
		redisService.deleteByPattern("finance:"+userId+":*");
		refreshTokenService.deleteByUserId(userId);
	}
	
}
