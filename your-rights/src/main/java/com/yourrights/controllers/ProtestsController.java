package com.yourrights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping(value = "{id}")
    public Protest getProtest(@PathVariable("id") long id) {
        return protestService.getProtest(id);
    }
    
    @GetMapping(value = Constants.SEARCH + "/{city}")
    public Protests searchProtest(@PathVariable("city") String city) {
        return protestService.searchProtest(city);
    }
    
    @DeleteMapping()
    public void deleteProtest(@PathVariable("id") long id) {
        protestService.deleteProtest(id);
    }
    
    @GetMapping("/prueba")
    public Protest getProtest() {
        return null;
    }
    
}
