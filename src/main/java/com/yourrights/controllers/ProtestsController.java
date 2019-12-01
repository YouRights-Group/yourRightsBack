package com.yourrights.controllers;

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
import com.yourrights.beans.SearchRequest;
import com.yourrights.constants.Constants;
import com.yourrights.services.ProtestsService;

import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping(Constants.PROTEST_REST)
@CrossOrigin(origins = "*")
// TODO Mirar crossOrigin
public class ProtestsController {

    @Autowired
    private ProtestsService protestService;

    @PostMapping(Constants.CREATE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public void createProtest(@RequestBody Protest protest) {
	protestService.createProtest(protest);
    }

    @GetMapping(value = Constants.LIST + "/{page}")
    public Protests getProtests(@PathVariable("page") int page) {
	return protestService.getProtests(page - 1);
    }

    @GetMapping(value = "{id}")
    public Protest getProtest(@PathVariable("id") long id) {
	return protestService.getProtest(id);
    }

    @PostMapping(value = Constants.SEARCH)
    public Protests searchProtest(@RequestBody SearchRequest request) {
	return protestService.searchProtest(request);
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
