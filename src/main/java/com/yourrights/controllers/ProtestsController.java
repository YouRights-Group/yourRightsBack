package com.yourrights.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
// TODO Mirar crossOrigin
public class ProtestsController {

    @Autowired
    private ProtestsService protestService;

    @PostMapping(Constants.CREATE)
    public void createProtest(@RequestBody Protest protest) {
        protestService.createProtest(protest);
    }

    @GetMapping(value = Constants.LIST + "/{pos}")
    public Protests getProtests(@PathVariable("pos") int pos) {
        return protestService.getProtests(pos);
    }
    
    @GetMapping(value = "{id}")
    public Protest getProtest(@PathVariable("id") long id) {
        return protestService.getProtest(id);
    }
    
    @GetMapping(value = Constants.SEARCH + "/{city}"+"/{date}")
    public Protests searchProtest(@PathVariable("city") String city, @PathVariable("city") Date date) {
        return protestService.searchProtest(city);
    }
    
    // TODO: por nombre
    // TODO: Ciudad
    // TODO: por fecha
    // TODO: por país
    // TODO: por area
    
    @DeleteMapping(value = "delete/" + "/{id}")
    public void deleteProtest(@PathVariable("id") long id) {
        protestService.deleteProtest(id);
    }
    
}
