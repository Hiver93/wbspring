package com.kdw.wb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.common.BaseResBody;
import com.kdw.wb.dto.request.WhiteboardReqDto;
import com.kdw.wb.facade.WhiteboardFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/whiteboard")
@RequiredArgsConstructor
public class WhiteBoardController {
	
	private final WhiteboardFacade whiteboardFacade;
	
	@GetMapping("/overview")
	public Object getOverview(
			@RequestBody WhiteboardReqDto.Get dto
			){
		return this.whiteboardFacade.getOverview(dto);
	}
	
	@PutMapping("/overview")
	public ResponseEntity<BaseResBody<Void>> postWhiteboard(
			@RequestParam("file") MultipartFile file
			){
		this.whiteboardFacade.updateAllByCsv(file);
		return new BaseResBody<Void>(null,"success").toResponse(HttpStatus.OK);
	}
}
