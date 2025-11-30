package com.kdw.wb.facade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdw.wb.dto.legacy.ResDto;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.service.AuthService;
import com.kdw.wb.service.SalesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WhiteboardFacade {

	private final SalesService salesService;
	private final AuthService authService;
	
	public List<ResDto.OverviewItem> getOverview(){
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		List<ResDto.OverviewItem> list = this.salesService.getSalesList().stream().map(ResDto.OverviewItem::from).toList();
		return list;
	}
}
