package dev.ashishwagh.novaspend.service;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.mapper.UserMapper;
import dev.ashishwagh.novaspend.model.User;
import dev.ashishwagh.novaspend.repository.UserRepository;
@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Override
	public UserResponse signup(SignUpRequest request) {
		Optional<User> user=userRepository.findByEmail(request.getEmail());
		if(user.isPresent())
		{
			throw new RuntimeException("Email Already Registered");
		}
		User newUser=userMapper.toUser(request);
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String hashedPass=bCryptPasswordEncoder.encode(request.getPassword());
		newUser.setPassword(hashedPass);
		User savedUser=userRepository.save(newUser);
		return userMapper.toUserResponse(savedUser);
	} 

	@Override
	public UserResponse login(LoginRequest request) {
		Optional<User> requested=userRepository.findByEmail(request.getEmail());
		if(!requested.isPresent())
		{
			throw new RuntimeException("Invalid email or password");
		}
		User user=requested.get();
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		if(!bCryptPasswordEncoder.matches(request.getPassword(),user.getPassword()))
		{
			throw new RuntimeException("Invalid email or password");
		}
		return userMapper.toUserResponse(user);
	}
}
