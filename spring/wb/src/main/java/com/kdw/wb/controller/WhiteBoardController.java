package com.kdw.wb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdw.wb.dto.legacy.ResDto.OverviewItem;
import com.kdw.wb.service.SalesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/whiteboard")
@RequiredArgsConstructor
public class WhiteBoardController {
	
	private final SalesService salesService;

	@GetMapping("/overview")
	public Object getOverview(){
		
		
		return this.salesService.getSalesList().stream()
				.map(OverviewItem::from)
				.toList();
	}
}
