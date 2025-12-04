package com.kdw.wb.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.SalesStatus;
import com.kdw.wb.dto.legacy.ResDto;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.service.AuthService;
import com.kdw.wb.service.CompanyService;
import com.kdw.wb.service.ContractService;
import com.kdw.wb.service.EngineerService;
import com.kdw.wb.service.FileService;
import com.kdw.wb.service.SalesService;
import com.kdw.wb.util.TimeFormatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WhiteboardFacade {
	private final SalesService salesService;
	private final AuthService authService;
	private final FileService fileService;
	private final EngineerService engineerService;
	private final CompanyService companyService;
	private final ContractService contractService;
	private final TimeFormatUtil timeFormatUtil;

	public List<ResDto.OverviewItem> getOverview(){
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		List<ResDto.OverviewItem> list = this.salesService.getSalesList().stream().map(ResDto.OverviewItem::from).toList();
		return list;
	}
	
	/**
	 * @param file
	 */
	public void updateAllByCsv(MultipartFile file) {
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		List<ContractInfo> contractInfoList = this.fileService.convertFromCsv(file);
		
		SalesStatus salesStatus = this.salesService.getSalesStatus("在職中");
		EngineerType engineerType = this.engineerService.getEngineerType("正社員");
		
		contractInfoList.stream().map(ContractInfo::getSalesName).distinct()
		.forEach((str)->{this.salesService.createSales(str, salesStatus);});

		contractInfoList.stream().map(ContractInfo::getCompanyName).distinct()
		.forEach((str)->{this.companyService.createCompany(str);});
		
		contractInfoList.stream()
		.forEach((info)->{
			Engineer engineer = this.engineerService.createEngineer(0, info.getEngineerName(), engineerType);
			this.contractService.createContract(
					engineer, 
					salesService.getSales(info.getSalesName()), 
					companyService.getCompany(info.getCompanyName()), 
					timeFormatUtil.convert(info.getStartDate()), 
					timeFormatUtil.convert(info.getEndDate()));
		});
	}
}
