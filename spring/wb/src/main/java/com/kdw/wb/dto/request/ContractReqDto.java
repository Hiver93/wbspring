package com.kdw.wb.dto.request;

import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ContractReqDto {

	@Validated
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Post{
		
		@NotNull(message = "salesId is required")
		private Integer salesId;
		@NotNull(message = "selfContractingId is required")
		private Integer selfContractingId;
		@NotNull(message = "engineerId is required")
		private Integer engineerId;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	    private LocalDateTime startDate;
		@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
		private LocalDateTime endDate;
	}
}
