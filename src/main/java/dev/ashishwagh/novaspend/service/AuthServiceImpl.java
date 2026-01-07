package dev.ashishwagh.novaspend.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.mapper.UserMapper;
import dev.ashishwagh.novaspend.model.User;
import dev.ashishwagh.novaspend.repository.UserRepository;
import dev.ashishwagh.novaspend.security.AuthUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final AuthenticationManager authenticationManager;
	private final AuthUtil authUtil;
	@Override
	public UserResponse signup(SignUpRequest request) {
		Optional<User> user=userRepository.findByEmail(request.getEmail());
		if(user.isPresent())
		{
			throw new RuntimeException("Email Already Registered To User");
		}
		User newUser=userMapper.toUser(request);
		newUser.setPassword(passwordEncoder.encode(request.getPassword()));
		User savedUser=userRepository.save(newUser);
		return userMapper.toUserResponse(savedUser);
	} 
	
	@Override
	public UserResponse login(LoginRequest request) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user=(User) authentication.getPrincipal();
		String token=authUtil.generateJWTToken(user);
		UserResponse response=userMapper.toUserResponse(user);
		response.setJwt(token);
		return response;
	}
}
