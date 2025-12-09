package com.kdw.wb.facade;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.service.EngineerService;
import com.kdw.wb.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EngineerFacade {
	
	private final EngineerService engineerService;
	private final FileService fileService;
	
	public void updateReturnees(MultipartFile file, LocalDateTime localDateTime) {
		Map<String, List<String>> map = this.fileService.convertFromCsvToMap(file);
		Map<Integer, String> engineerMap = new HashMap<>();
		List<String> idList = map.get("エンジニアID");
		List<String> statusList = map.get("ステータス");
		
		for(int i = 0; i < idList.size(); ++i) {
			engineerMap.put(Integer.valueOf(idList.get(i)), statusList.get(i));			
		}
		
		this.engineerService.updateReturnees(engineerMap, LocalDateTime.now());
	}
}
