package com.yourrights.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourrights.beans.Protests;
import com.yourrights.constants.Constants;
import com.yourrights.controllers.beans.Protest;

@RestController
@RequestMapping(Constants.PROTEST_REST)
public class ProtestsController {

	@PostMapping(Constants.CREATE)
	public void createProtest(@RequestBody Protest protest) {
		System.out.println(protest.toString());
	}
	
	@GetMapping(Constants.LIST)
	public Protests getProtests() {
		return new Protests();
	}
}
