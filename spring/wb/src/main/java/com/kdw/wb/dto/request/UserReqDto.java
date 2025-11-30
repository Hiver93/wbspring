package com.kdw.wb.dto.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReqDto {
	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post{
		
		@NotBlank(message = "username is required")
		private String username;
		
		@NotBlank(message = "password is required")
		private String password;
	}
}
