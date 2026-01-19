package dev.ashishwagh.novaspend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ashishwagh.novaspend.dto.LoginRequest;
import dev.ashishwagh.novaspend.dto.SignUpRequest;
import dev.ashishwagh.novaspend.dto.UserAuthResponse;
import dev.ashishwagh.novaspend.exception.InvalidTokenException;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.exception.UserAlreadyExistException;
import dev.ashishwagh.novaspend.exception.UserBlockedException;
import dev.ashishwagh.novaspend.exception.UserDeletedException;
import dev.ashishwagh.novaspend.mapper.UserAuthMapper;
import dev.ashishwagh.novaspend.model.RefreshToken;
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
	private final RefreshTokenService refreshTokenService;
	@Override
	public UserAuthResponse signup(SignUpRequest request) {
		Optional<User> user=userRepository.findByEmail(request.getEmail());
		if (user.isPresent()) {
			User existed=user.get();
			if(Status.ACTIVE.equals(existed.getStatus())||Status.BLOCKED.equals(existed.getStatus()))
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
		if (Status.BLOCKED.equals(user.getStatus()))
		    throw new UserBlockedException("User Suspended");
		if (Status.DELETED.equals(user.getStatus()))
		    throw new UserDeletedException("User Not Exists");
		String token=authUtil.generateJWTToken(user);
		RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getId());
		UserAuthResponse response=userAuthMapper.toUserResponse(user);
		response.setJwt(token);
		response.setRefreshToken(refreshToken.getToken());
		return response;
	}
	@Override
	public void logout(String refreshToken)
	{
		RefreshToken token=refreshTokenService.findByToken(refreshToken).orElseThrow(()->new InvalidTokenException("Invalid Refresh Token "));
		refreshTokenService.deleteByUserId(token.getUserId());
	}
	@Transactional
	@Override
	public UserAuthResponse refreshAccessToken(String refreshToken) {
		RefreshToken oldToken=refreshTokenService.findByToken(refreshToken).orElseThrow(()->new InvalidTokenException("Invalid Token"));
		oldToken=refreshTokenService.verifyExpiration(oldToken);
		User user=userRepository.findById(oldToken.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		RefreshToken newRefreshToken=refreshTokenService.createRefreshToken(user.getId());
		String newAccessToken = authUtil.generateJWTToken(user);
		UserAuthResponse response=userAuthMapper.toUserResponse(user);
		response.setJwt(newAccessToken);
		response.setRefreshToken(newRefreshToken.getToken());
		return response;
	}
}
