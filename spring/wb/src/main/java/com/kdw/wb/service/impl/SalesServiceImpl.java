package com.kdw.wb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.domain.sales.SalesStatus;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.repository.SalesRepository;
import com.kdw.wb.repository.SalesStatusRepository;
import com.kdw.wb.service.SalesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService{
	
	private final SalesRepository salesRepository;
	private final SalesStatusRepository salesStatusRepository;

	@Override
	public Sales createSales(String name, SalesStatus status) {
		Sales sales = Sales.builder().name(name).status(status).build();
		return this.salesRepository.save(sales);
	}

	@Override
	public Sales getSales(Integer salesId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sales> getSalesList() {
		// TODO Auto-generated method stub
		return this.salesRepository.findAll();
	}

	@Override
	public void removeSales(Integer salesId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifySales(Integer salesId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalesStatus getSalesStatus(String statusName) {
		return this.salesStatusRepository.findByname(statusName).orElseThrow(()->{throw new WhiteboardException(ErrorCode.CONTENT_NOT_FOUND);});
	}

	@Override
	public Sales getSales(String name) {
		return this.salesRepository.findByName(name).orElseThrow(()->{throw new WhiteboardException(ErrorCode.SALES_NOT_FOUND);});
	}

	
}
