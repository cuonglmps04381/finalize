package com.example.webdemo.service;

import com.example.webdemo.model.User;


public interface UserService {
	 User findUserByEmail(String email);
}
