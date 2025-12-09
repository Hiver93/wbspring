package com.kdw.wb.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kdw.wb.common.BaseResBody;
import com.kdw.wb.facade.EngineerFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/engineers")
@RequiredArgsConstructor
public class EngineerController {
	
	private final EngineerFacade engineerFacade;
	
	@PatchMapping("/returnees")
	public ResponseEntity<BaseResBody<Void>> updateRetunee(@RequestParam("file") MultipartFile file){
		this.engineerFacade.updateReturnees(file, LocalDateTime.now());
		return new BaseResBody<Void>(null,"成功的に反映されました。").toResponse(HttpStatus.OK);
	}
}
