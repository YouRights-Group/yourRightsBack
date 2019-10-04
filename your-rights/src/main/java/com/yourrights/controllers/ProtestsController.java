package com.yourrights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.constants.Constants;
import com.yourrights.services.ProtestsService;

@RestController
@RequestMapping(Constants.PROTEST_REST)
public class ProtestsController {

    @Autowired
    private ProtestsService protestService;

    @PostMapping(Constants.CREATE)
    public void createProtest(@RequestBody Protest protest) {
        protestService.createProtest(protest);
    }

    @GetMapping(Constants.LIST)
    public Protests getProtests() {
        return protestService.getProtests();
    }
}
