package dev.ashishwagh.novaspend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserAuthResponse;
import dev.ashishwagh.novaspend.exception.UserAlreadyExistException;
import dev.ashishwagh.novaspend.exception.UserBlockedException;
import dev.ashishwagh.novaspend.exception.UserDeletedException;
import dev.ashishwagh.novaspend.mapper.UserAuthMapper;
import dev.ashishwagh.novaspend.model.Roles;
import dev.ashishwagh.novaspend.model.Status;
import dev.ashishwagh.novaspend.model.User;
import dev.ashishwagh.novaspend.repository.UserRepository;
import dev.ashishwagh.novaspend.security.AuthUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserAuthMapper userAuthMapper;
	private final AuthenticationManager authenticationManager;
	private final AuthUtil authUtil;
	@Override
	public UserAuthResponse signup(SignUpRequest request) {
		Optional<User> user=userRepository.findByEmail(request.getEmail());
		if (user.isPresent()) {
			User existed=user.get();
			if(existed.getStatus().equals(Status.ACTIVE)||existed.getStatus().equals(Status.BLOCKED))
				throw new UserAlreadyExistException("Email already registered");
			else
			{
				existed.setStatus(Status.ACTIVE);
				existed.setPassword(passwordEncoder.encode(request.getPassword()));
				existed.setRole(Roles.USER);
				User savedUser=userRepository.save(existed);
				return userAuthMapper.toUserResponse(savedUser);
			}
		}
		User newUser=userAuthMapper.toUser(request);
		newUser.setPassword(passwordEncoder.encode(request.getPassword()));
		newUser.setRole(Roles.USER);
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setStatus(Status.ACTIVE);
		User savedUser=userRepository.save(newUser);
		return userAuthMapper.toUserResponse(savedUser);
	} 
	
	@Override
	public UserAuthResponse login(LoginRequest request) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user=(User) authentication.getPrincipal();
		if(user.getStatus().equals(Status.BLOCKED))
			throw new UserBlockedException("User Suspended");
		if(user.getStatus().equals(Status.DELETED))
			throw new UserDeletedException("User Not Exists");
		String token=authUtil.generateJWTToken(user);
		UserAuthResponse response=userAuthMapper.toUserResponse(user);
		response.setJwt(token);
		return response;
	}
}
