package dev.ashishwagh.novaspend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.UserResponse;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.mapper.UserMapper;
import dev.ashishwagh.novaspend.model.Status;
import dev.ashishwagh.novaspend.model.User;
import dev.ashishwagh.novaspend.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
	private final UserRepository userRepo;
	private final UserMapper userMapper;
	@Override
	public List<UserResponse> getUsers()
	{
		List<User> users=userRepo.findAll();
		return users.stream().map(userMapper::toUserResponse).toList();
	}
	@Override
	public UserResponse getUserById(String id) {
		User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
		return userMapper.toUserResponse(user);
	}
	@Override
	public UserResponse blockUser(String id) {
		User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
		if(user.getStatus()==Status.DELETED)
			throw new IllegalStateException("Deleted user cannot be blocked");
		if(user.getStatus()==Status.BLOCKED)
			throw new IllegalStateException("User is already blocked");
		user.setStatus(Status.BLOCKED);
		userRepo.save(user);
		return userMapper.toUserResponse(user);
	}
	@Override
	public UserResponse deleteUser(String id) {
		User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
		if(user.getStatus()==Status.DELETED)
			throw new IllegalStateException("User already deleted");
		user.setStatus(Status.DELETED);
		userRepo.save(user);
		return userMapper.toUserResponse(user);
	}
	@Override
	public UserResponse reactiveUser(String id) {
		User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
		if (user.getStatus() == Status.ACTIVE)
            throw new IllegalStateException("User is already active");
		user.setStatus(Status.ACTIVE);
		userRepo.save(user);
		return userMapper.toUserResponse(user);
	}
}
