package com.kdw.wb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdw.wb.common.BaseResBody;
import com.kdw.wb.dto.request.SalesReqDto;
import com.kdw.wb.dto.response.SalesResDto;
import com.kdw.wb.facade.SalesFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {
	
	private final SalesFacade salesFacade;
	
	@PostMapping
	public ResponseEntity<BaseResBody<SalesResDto.Created>> postSales(@RequestBody @Valid SalesReqDto.Post dto){
		return new BaseResBody<>(salesFacade.createSales(dto),"営業さんが追加されました。").toResponse(HttpStatus.CREATED);
	}
}
