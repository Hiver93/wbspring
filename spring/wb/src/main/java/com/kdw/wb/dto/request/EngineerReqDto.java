package com.kdw.wb.dto.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EngineerReqDto {
	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post{
		
		@NotBlank(message = "name is required")
		private String name;
		@NotNull(message = "engineerNo is required")
		private Integer engineerNo;
		@NotBlank(message = "engineerStatus is required")
		private String engineerStatus;
		@NotBlank(message = "engineerStatus is required")
		private String engineerType;
	}
}
