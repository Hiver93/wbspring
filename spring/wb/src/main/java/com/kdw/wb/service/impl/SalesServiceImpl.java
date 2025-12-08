package com.kdw.wb.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
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
	public Sales createSales(Integer no, String name, SalesStatus status) {
		Sales sales = Sales.builder().no(no).name(name).status(status).build();
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

	@Override
	public Sales getSalesByNo(Integer no) {
		return this.salesRepository.findByNo(no).orElseThrow(()->{throw new WhiteboardException(ErrorCode.SALES_NOT_FOUND);});
	}

	@Override
	public void ensureSales(List<ContractInfo> contractInfoList) {
		List<Integer> noList = contractInfoList.stream().map(i->Integer.valueOf(i.getSalesNo())).toList();
		
		Set<Integer> noSet = this.salesRepository.findAllByNoIn(noList).stream().map(Sales::getNo).collect(Collectors.toSet());
		SalesStatus salesStatus = this.salesStatusRepository.findByname("在職中").orElseThrow();
		List<Sales> salesList =
		contractInfoList.stream()
			.filter(i-> !noSet.contains(Integer.valueOf(i.getSalesNo())))
			.map(info -> Sales.builder()
					.no(Integer.valueOf(info.getSalesNo()))
					.name(info.getSalesName())
					.status(salesStatus)
					.build())
			.collect(Collectors.toMap(
			        Sales::getNo,  
			        user -> user,          
			        (oldValue, newValue) -> oldValue 
			    ))
			.values().stream()
			.toList();
		
		this.salesRepository.saveAll(salesList);
	}

	
}
