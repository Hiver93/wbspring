package com.kdw.wb.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdw.wb.common.BaseResBody;
import com.kdw.wb.dto.request.AuthReqDto;
import com.kdw.wb.facade.AuthFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {	
	
	private final AuthFacade authFacade;
	
	@PostMapping("/token")
	public ResponseEntity<BaseResBody<Void>> login(@Valid @RequestBody AuthReqDto.Login dto){
		this.authFacade.login(dto);
		return new BaseResBody<Void>(null, "認証が成功しました。").toResponse(HttpStatus.OK);
	}
	
	@PostMapping("/token/logout")
	public ResponseEntity<BaseResBody<Void>> logout(){
		this.authFacade.logout();
		return new BaseResBody<Void>(null, "認証情報が削除されました。").toResponse(HttpStatus.OK);
	}
	
	@PostMapping("/token/refresh")
	public ResponseEntity<BaseResBody<Void>> refresh(){
		this.authFacade.refresh();
		return new BaseResBody<Void>(null, "認証情報を更新しました。").toResponse(HttpStatus.OK);
	}
}