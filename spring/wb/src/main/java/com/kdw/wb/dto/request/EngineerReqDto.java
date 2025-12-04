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
		
		@NotNull(message = "engineerNo is required")
		private Integer engineerNo;
		@NotBlank(message = "name is required")
		private String name;
		@NotBlank(message = "engineerType is required")
		private String engineerType;
		private String joiningDate;
		private String leavingDate;
	}
	
	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Put{
		
		@NotNull(message = "engineerNo is required")
		private Integer engineerNo;
		@NotBlank(message = "name is required")
		private String engineerName;
		@NotBlank(message = "engineerType is required")
		private String engineerType;
		private String joiningDate;
		private String leavingDate;
	}
}
