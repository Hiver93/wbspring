package com.kdw.wb.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.domain.contract.ContractInfo;

public interface FileService {
	public List<ContractInfo> convertFromCsv(MultipartFile file);
	public Map<String,List<String>> convertFromCsvToMap(MultipartFile file);
	
}
