package com.kdw.wb.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whiteboard")
public class WhiteBoardController {

	@GetMapping("/overview")
	public Object getOverview(){
		return Map.of(
				"i",123,
				"abc","de"
				);
	}
}
