package com.kdw.wb.dto.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CompanyReqDto {
	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post{
		
		@NotBlank(message = "name is required")
		private String name;
		@NotBlank(message = "password is required")
		private String salesStatus;
	}
}
