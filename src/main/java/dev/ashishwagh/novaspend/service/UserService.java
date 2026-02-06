package dev.ashishwagh.novaspend.service;

import dev.ashishwagh.novaspend.dto.UserResponse;

public interface UserService {

	UserResponse getUserDetails(String userId);

	UserResponse changeUserPassword(String userID, String oldPassword, String newPassword1, String newPassword2);

	void deleteUser(String userId);

}
