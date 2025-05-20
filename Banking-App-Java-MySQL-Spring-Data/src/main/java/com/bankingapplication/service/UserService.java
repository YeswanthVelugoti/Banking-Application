package com.bankingapplication.service;

import java.util.List;

import com.bankingapplication.dto.UserDTO;

public interface UserService {
	UserDTO getUserById(Long id);

	UserDTO createUser(UserDTO userDTO);

	UserDTO updateUserById(Long id, UserDTO userDTO);

	Boolean deleteUserById(Long id);

	List<UserDTO> searchUsersByName(String name);
}
