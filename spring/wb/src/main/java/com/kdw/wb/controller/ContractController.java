package com.kdw.wb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdw.wb.common.BaseResBody;

@RestController
@RequestMapping("/contract")
public class ContractController {

	@PutMapping("/bulk")
	public ResponseEntity<BaseResBody<Void>> updateBulk(){
		
		return new BaseResBody<Void>(null, "success").toResponse(HttpStatus.OK);
	}
}
