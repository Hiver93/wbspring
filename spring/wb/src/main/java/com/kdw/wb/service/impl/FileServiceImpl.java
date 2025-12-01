package com.kdw.wb.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
		try(InputStreamReader reader = new InputStreamReader(file.getInputStream(),"MS932")){
			CSVReader csvReader = new CSVReader(reader);
		    String[] headers = csvReader.readNext(); // 첫 번째 행(헤더)을 읽습니다.	
		    if (headers != null) {
		        System.out.println("--- CSV 파일에서 읽은 실제 헤더 ---");
		        for (String header : headers) {
		            System.out.println("['" + header.trim() + "']"); // .trim()으로 공백 제거 후 출력
		        }
		    }
		    csvReader.forEach(line -> {
		    	String[] strs = line;
		    	list.add(ContractInfo.builder()
		    			.companyName(strs[0])
		    			.engineerName(strs[1])
		    			.salesName(strs[2])
		    			.startDate(strs[3])
		    			.endDate(strs[4])
		    			.build());
		    });
		  list.stream().forEach(System.out::println);
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
