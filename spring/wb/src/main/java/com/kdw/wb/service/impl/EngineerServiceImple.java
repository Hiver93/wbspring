package com.kdw.wb.service.impl;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.repository.EngineerRepository;
import com.kdw.wb.repository.EngineerTypeRepository;
import com.kdw.wb.service.EngineerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EngineerServiceImple implements EngineerService{
	
	private final EngineerRepository engineerRepository;
	private final EngineerTypeRepository engineerTypeRepository;
	
	@Override
	public Engineer createEngineer(Integer id, String name, EngineerType engineerType, Boolean companyHouse) {
		Engineer engineer = Engineer.builder().id(id).name(name).type(engineerType).companyHouse(companyHouse).build();
		return this.engineerRepository.save(engineer);
	}

	@Override
	public void modifyEngineer(Integer engineerId, String name, Company company, Sales sales) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Engineer getEngineer(Integer engineerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Engineer> getEngineerListByCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Engineer> getEngineerListBySales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEngineer(Integer engineerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EngineerType getEngineerType(String typeName) {
		return this.engineerTypeRepository.findByName(typeName).orElseThrow(()->{throw new WhiteboardException(ErrorCode.CONTENT_NOT_FOUND);});
	}

	@Override
	public List<Engineer> getEngineerList() {
		return this.engineerRepository.findAll();
	}

	@Override
	public void ensureEngineers(List<ContractInfo> contractInfoList) {
		
		List<Integer> idList = contractInfoList.stream().map(i->Integer.valueOf(i.getEngineerId())).toList();
		
		Set<Integer> idSet = this.engineerRepository.findAllByIdIn(idList).stream().map(Engineer::getId).collect(Collectors.toSet());
		Map<String, EngineerType> engineerTypeMap = Map.of(
				"正社員", this.engineerTypeRepository.findByName("正社員").orElseThrow(),
				"GE", this.engineerTypeRepository.findByName("GE").orElseThrow(),
				"契約社員", this.engineerTypeRepository.findByName("契約社員").orElseThrow(),
				"新卒", this.engineerTypeRepository.findByName("新卒").orElseThrow()
				);
		
		List<Engineer> engineerList =
		contractInfoList.stream()
			.filter(i-> !idSet.contains(Integer.valueOf(i.getEngineerId())))
			.map(info -> Engineer.builder()
					.id(Integer.valueOf(info.getEngineerId()))
					.name(info.getEngineerName())
					.type(engineerTypeMap.get(info.getType()))
					.companyHouse(info.getCompanyHouse().equals("有"))
					.build())
			.collect(Collectors.toMap(
			        Engineer::getId,  
			        user -> user,          
			        (oldValue, newValue) -> oldValue 
			    ))
			.values().stream()
			.toList();
		
		this.engineerRepository.saveAll(engineerList);
	}

	@Override
	@Transactional
	public void updateReturnees(Map<Integer, String> returneesMap, LocalDateTime localDateTime) {
		returneesMap.entrySet().stream().forEach(entry->{
			Integer id = entry.getKey();
			String status = entry.getValue();
			
			Engineer saved = this.engineerRepository.findById(id).orElseThrow(()->{throw new WhiteboardException(ErrorCode.ENGINEER_NOT_FOUND);});
			saved.getContractList().stream().filter(c->c.isOver(YearMonth.from(localDateTime)))
			.forEach(c ->{
					switch(status) {
					case "確定":
						c.terminateAfterEndDate();
						break;
					case "退職":
						c.terminateAfterEndDate();
						c.getEngineer().setLeavingDate(localDateTime);
						break;
					}
			});
		
		});
	}

}
