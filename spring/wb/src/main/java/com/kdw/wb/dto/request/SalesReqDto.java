package com.kdw.wb.dto.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SalesReqDto {
	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post{
		
		@NotBlank(message = "no is required")
		private Integer no;
		@NotBlank(message = "name is required")
		private String name;
	}
}
