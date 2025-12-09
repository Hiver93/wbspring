package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.domain.sales.SalesStatus;

public interface SalesService {

	public Sales createSales(Integer id, String name, SalesStatus status);
	public Sales getSales(Integer salesId);
	public List<Sales> getSalesList();
	public void removeSales(Integer salesId);
	public void modifySales(Integer salesId, String name);
	public SalesStatus getSalesStatus(String statusName);
	public Sales getSales(String name);
	public void ensureSales(List<ContractInfo> contractInfoList);
}
