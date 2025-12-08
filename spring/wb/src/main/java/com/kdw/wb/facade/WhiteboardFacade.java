package com.kdw.wb.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.domain.contract.ContractInfo;
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
		
		contractInfoList.stream().forEach(i->{
			if(!salesInfoMap.containsKey(i.getSalesNo())) {
				salesInfoMap.put(i.getSalesNo(), i);
			}
			if(!i.getCompanyNo().equals("3729") && !i.getCompanyNo().equals("3906") && !engineerInfoMap.containsKey(i.getEngineerNo())) {
				engineerInfoMap.put(i.getEngineerNo(),i);
			}
			if(!companyInfoMap.containsKey(i.getCompanyNo())) {
				companyInfoMap.put(i.getCompanyNo(), i);
			}
		});
		
//		406, 3729, 3906

		this.engineerService.ensureEngineers(engineerInfoMap.entrySet().stream().map(Entry::getValue).toList());
		this.salesService.ensureSales(salesInfoMap.entrySet().stream().map(Entry::getValue).toList());
		this.companyService.ensureCompanies(companyInfoMap.entrySet().stream().map(Entry::getValue).toList());	
		this.contractService.ensureContracts(
				contractInfoList.stream().filter(info->!info.getCompanyNo().equals("3729") && !info.getCompanyNo().equals("3906") && !info.getCompanyNo().equals("406"))
				.toList()
				);
		
		
	}
}
