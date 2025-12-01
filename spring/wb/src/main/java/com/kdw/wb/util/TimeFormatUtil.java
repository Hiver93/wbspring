package com.kdw.wb.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class TimeFormatUtil {

	/***
	 * 
	 * @param str(yyyy/M/d)
	 * @return startOfDay
	 */
	public LocalDateTime convert(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        
        LocalDate localDate1 = LocalDate.parse(dateStr, formatter);
        LocalDateTime localDateTime = localDate1.atStartOfDay();
        return localDateTime;
	}
}
