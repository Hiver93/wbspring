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
		
		@NotBlank(message = "companyId is required")
		private Integer companyId;
		@NotBlank(message = "companyName is required")
		private String companyName;
	}
}
