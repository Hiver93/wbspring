package com.kdw.wb.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.SalesStatus;
import com.kdw.wb.dto.legacy.ResDto;
import com.kdw.wb.dto.response.WhiteboardResDto;
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

	public List<ResDto.OverviewItem> getOverviewLegacy(){
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		
		List<ResDto.OverviewItem> list = this.salesService.getSalesList().stream().map(ResDto.OverviewItem::from).toList();
		return list;
	}
	
	public WhiteboardResDto.Overview getOverview(){
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		WhiteboardResDto.Overview overview = WhiteboardResDto.Overview.from(this.engineerService.getEngineerList(), this.salesService.getSalesList());
		return overview;
	}
	
	/**
	 * @param file
	 */
	public void updateAllByCsv(MultipartFile file) {
		if(!authService.isAuthenticated()) {
			throw new WhiteboardException(ErrorCode.AUTHENTICATION_REQUIRED);
		}
		List<ContractInfo> contractInfoList = this.fileService.convertFromCsv(file);
		
		Map<String, ContractInfo> salesInfoMap = new HashMap<>();
		Map<String, ContractInfo> engineerInfoMap = new HashMap<>();
		Map<String, ContractInfo> companyInfoMap = new HashMap<>();
		
		SalesStatus salesStatus = this.salesService.getSalesStatus("在職中");
		Map<String, EngineerType> engineerTypeMap = Map.of(
				"正社員", this.engineerService.getEngineerType("正社員"),
				"GE", this.engineerService.getEngineerType("GE"),
				"契約社員", this.engineerService.getEngineerType("契約社員"),
				"新卒", this.engineerService.getEngineerType("新卒")
				);
		
		contractInfoList.stream().forEach(i->{
			if(!salesInfoMap.containsKey(i.getSalesNo())) {
				salesInfoMap.put(i.getSalesNo(), i);
			}
			if(!engineerInfoMap.containsKey(i.getEngineerNo())) {
				engineerInfoMap.put(i.getEngineerNo(),i);
			}
			if(!companyInfoMap.containsKey(i.getCompanyNo())) {
				companyInfoMap.put(i.getCompanyNo(), i);
			}
		});
//		406
//		3729
//		3906
//		3729
		System.out.println(salesInfoMap.size());
		System.out.println(engineerInfoMap.size());
		System.out.println(companyInfoMap.size());

		engineerInfoMap.entrySet().stream().forEach(en->{
			ContractInfo info = en.getValue();
			if(info.getCompanyNo().equals("3729") || info.getCompanyNo().equals("3906")) {
				
			}
			else {
				this.engineerService.createEngineer(Integer.valueOf(info.getEngineerNo()), info.getEngineerName(), engineerTypeMap.get(info.getType()), info.getCompanyHouse().equals("有"));
			}
		});
		salesInfoMap.entrySet().stream().forEach(en->{
			ContractInfo info = en.getValue();
			this.salesService.createSales(Integer.valueOf(info.getSalesNo()), info.getSalesName(), salesStatus);
		});
		companyInfoMap.entrySet().stream().forEach(en->{
			ContractInfo info = en.getValue();
			this.companyService.createCompany(Integer.valueOf(info.getCompanyNo()), info.getCompanyName());
		});

		
		
		contractInfoList.stream()
		.forEach((info)->{
			if(info.getCompanyNo().equals("3729") || info.getCompanyNo().equals("3906")||info.getCompanyNo().equals("406")){
				
			}
			else
			this.contractService.createContract(
					this.engineerService.getEngineerByNo(Integer.valueOf(info.getEngineerNo())),
					salesService.getSales(info.getSalesName()),
					companyService.getCompanyByNo(Integer.valueOf(info.getCompanyNo())),
					timeFormatUtil.convert(info.getStartDate()),
					timeFormatUtil.convert(info.getEndDate()),
					info.getTakeover().equals("1")? false : true);
		});
	}
}
