package com.kdw.wb.service;

import com.kdw.wb.domain.user.User;

public interface UserService {

	public User getUser(Integer userId);
	public void createUser(String username, String password);
	public User getUser(String username, String password);
}
