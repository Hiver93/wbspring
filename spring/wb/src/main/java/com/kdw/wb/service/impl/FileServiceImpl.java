package com.kdw.wb.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.service.FileService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public List<ContractInfo> convertFromCsv(MultipartFile file) {
		if(file.isEmpty()) {
			throw new WhiteboardException(ErrorCode.FILE_REQUIRED);
		}
		System.out.println(file.getContentType());
		if(!"text/csv".equals(file.getContentType())) {
			throw new WhiteboardException(ErrorCode.INVALID_CONTENT_TYPE);
		}
		List<ContractInfo> list = new ArrayList<ContractInfo>();
		try(InputStreamReader reader = new InputStreamReader(file.getInputStream(),"MS932");
			CSVReader csvReader = new CSVReader(reader);){
		    String[] headers = csvReader.readNext(); 
		    csvReader.forEach(line -> {
		    	String[] strs = line;
		    	list.add(ContractInfo.builder()
		    			.companyId(strs[0])
		    			.companyName(strs[1])
		    			.engineerId(strs[2])
		    			.engineerName(strs[3])
		    			.salesId(strs[4])
		    			.salesName(strs[5])
		    			.startDate(strs[6])
		    			.endDate(strs[7])
		    			.type(strs[8])
		    			.companyHouse(strs[9])
		    			.takeover(strs[10])
		    			.build());
		    });
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvValidationException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public Map<String, List<String>> convertFromCsvToMap(MultipartFile file) {
		if(file.isEmpty()) {
			throw new WhiteboardException(ErrorCode.FILE_REQUIRED);
		}
		
		if(!"text/csv".equals(file.getContentType())) {
			throw new WhiteboardException(ErrorCode.INVALID_CONTENT_TYPE);
		}
		List<ContractInfo> list = new ArrayList<ContractInfo>();
		Map<String,List<String>> map = new HashMap<>();
		List<String> headerNames = new ArrayList<>();
		try(InputStreamReader reader = new InputStreamReader(file.getInputStream(),"MS932");
			CSVReader csvReader = new CSVReader(reader);	
		){
			
		    String[] headers = csvReader.readNext(); 
		    Stream.of(headers).forEach(str->{
		    	map.put(str, new ArrayList<>());
		    	headerNames.add(str);
		    });
		    
		    csvReader.forEach(line -> {
		    	String[] strs = line;
		    	for(int i = 0; i < strs.length; ++i) {
		    		map.get(headerNames.get(i)).add(strs[i]);
		    	}
		    });
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvValidationException e) {
			throw new RuntimeException(e);
		}	
		
		return map;
	}
	
	
}
