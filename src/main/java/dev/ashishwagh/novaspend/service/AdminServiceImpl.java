package dev.ashishwagh.novaspend.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.PageResponse;
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
	public PageResponse<UserResponse> getUsers(int page,int size,Status status,LocalDateTime fromDate,LocalDateTime toDate)
	{
		if (page<0) page=0;
	    if (size<=0) size=10;
	    if (size>50) size=50;
		Page<UserResponse> userspage=userRepo.filterUsers(page,size,status,fromDate,toDate);
		return new PageResponse<>(
				userspage.getContent(),
				userspage.getNumber(),
				userspage.getSize(),
				userspage.getTotalElements(),
				userspage.getTotalPages(),
				userspage.hasPrevious(),
				userspage.hasNext()
				);
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
