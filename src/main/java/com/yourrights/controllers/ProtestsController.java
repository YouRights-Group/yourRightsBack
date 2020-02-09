package com.yourrights.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.beans.SearchRequest;
import com.yourrights.constants.Constants;
import com.yourrights.services.ProtestsService;

import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping(Constants.PROTEST_REST)
@CrossOrigin(origins = "*")
public class ProtestsController {

    @Autowired
    private ProtestsService protestService;

    @PatchMapping(value = "{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public void changeProtest(@RequestBody Protest protest) {
	protestService.changeProtest(protest);
    }

    @PostMapping(Constants.CREATE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public void createProtest(@RequestBody Protest protest) {
	protestService.createProtest(protest);
    }
    
    @PostMapping(Constants.LOADFILE + "/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public void createProtest(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
	protestService.loadDocument(file, id);
    }

    @DeleteMapping(value = Constants.DELETE + "/{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public void deleteProtest(@PathVariable("id") long id) {
	protestService.deleteProtest(id);
    }

    @GetMapping(value = "{id}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public Protest getProtest(@PathVariable("id") long id) {
	return protestService.getProtest(id);
    }

    @GetMapping(value = Constants.LIST + "/{page}")
    public Protests getProtests(@PathVariable("page") int page) {
	return protestService.getProtests(page - 1);
    }

    @PostMapping(value = Constants.SEARCH)
    public Protests searchProtest(@RequestBody SearchRequest request) {
	return protestService.searchProtest(request);
    }

}
