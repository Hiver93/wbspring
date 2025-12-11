package com.kdw.wb.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WhiteboardReqDto {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class Get{
		private LocalDate date;
	}
}
