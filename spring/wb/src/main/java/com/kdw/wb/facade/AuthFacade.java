package com.kdw.wb.facade;

import org.springframework.stereotype.Service;

import com.kdw.wb.dto.request.AuthReqDto;
import com.kdw.wb.service.AuthService;
import com.kdw.wb.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {

	private final AuthService authService;
	private final UserService userService;
	
	public void login(AuthReqDto.Login dto) {
		this.authService.setAuthentication(userService.getUser("admin", dto.getPassword()).getId());
	}
	
	public void refresh() {
		this.authService.refreshAuthentication();
	}
	
	public void logout() {
		this.authService.removeAuthentication();
	}
}
