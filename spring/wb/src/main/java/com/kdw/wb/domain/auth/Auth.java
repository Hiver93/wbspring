package com.kdw.wb.domain.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Auth {

	private Integer userId;
	
	@Builder
	public Auth(Integer userId) {
		super();
		this.userId = userId;
	}
}
