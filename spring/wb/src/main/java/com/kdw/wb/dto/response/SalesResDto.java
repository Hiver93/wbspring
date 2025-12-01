package com.kdw.wb.dto.response;

import com.kdw.wb.domain.sales.Sales;

public class SalesResDto {

	public record Created(
			Integer sales_id,
			String sales_name
			) {
		public static Created from(Sales sales) {
			return new Created(sales.getId(), sales.getName());
		}
	}
}
