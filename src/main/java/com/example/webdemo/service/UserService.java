package com.example.webdemo.service;

import com.example.webdemo.model.User;
import com.example.webdemo.model.dto.UserDTO;

import java.util.List;


public interface UserService {
	 UserDTO findUserByEmail(String email);
	 List<UserDTO> getAll();
}
