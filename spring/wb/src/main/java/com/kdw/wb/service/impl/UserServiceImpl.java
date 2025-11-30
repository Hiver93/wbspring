package com.kdw.wb.service.impl;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.user.User;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.repository.UserRepository;
import com.kdw.wb.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public User getUser(Integer userId) {
		return this.userRepository.findById(userId).orElseThrow(()->{throw new WhiteboardException(ErrorCode.USER_NOT_FOUND);});
	}

	@Override
	@Transactional
	public void createUser(String username, String password) {
		if(this.userRepository.existsByUsername(username)) {
			throw new WhiteboardException(ErrorCode.USERNAME_CONFLICT);
		}
		User user = User.builder().username(username).password(password).build();
		this.userRepository.save(user);
	}

	@Override
	public User getUser(String username, String password) {
		return this.userRepository.findByUsernameAndPassword(username, password).orElseThrow(()->{throw new WhiteboardException(ErrorCode.USER_NOT_FOUND);});
	}

}
