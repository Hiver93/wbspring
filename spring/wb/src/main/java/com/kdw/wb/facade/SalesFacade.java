package com.kdw.wb.facade;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.sales.SalesStatus;
import com.kdw.wb.dto.request.SalesReqDto;
import com.kdw.wb.dto.response.SalesResDto;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.service.AuthService;
import com.kdw.wb.service.SalesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesFacade {

	private final SalesService salesService;
	private final AuthService authService;
	
	public SalesResDto.Created createSales(SalesReqDto.Post dto){
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		SalesStatus status = this.salesService.getSalesStatus("在職中");
		return SalesResDto.Created.from(this.salesService.createSales(dto.getName(), status));
	}
}
