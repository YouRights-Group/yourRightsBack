package com.yourrights.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.ProtestsException;
import com.yourrights.repository.ProtestsRepository;
import com.yourrights.repository.beans.ProtestEntity;

@Service
public class ProtestsService {

    @Autowired
    private ProtestsRepository repository;

    public void createProtest(Protest protest) {

	if (validateProtest(protest)) {
	    ProtestEntity protestEntity = new ProtestEntity();
	    BeanUtils.copyProperties(protest, protestEntity);
	    repository.save(protestEntity);
	}
    }

    private boolean validateProtest(Protest protest) {
	// Validation 1: Protest with same city, whoDefends and date.
	List<ProtestEntity> list = repository.findByCityAndWhoDefendsAndDate(protest.getCity(), protest.getWhoDefends(),
		protest.getDate());
	if (list.size() > 0)
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_001, "Protest very similar");
	// Validation 2: Protest at same time in same city
	list = repository.findByCityAndDateAndTime(protest.getCity(), protest.getDate(), protest.getTime());
	if (list.size() > 0)
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_002,
		    "Protest in the same city at the same time");
	return true;
    }

    public Protests getProtests() {
	List<Protest> protestList = new ArrayList<Protest>();
	repository.findAll().forEach(entity -> {
	    Protest p = new Protest();
	    // TODO Solo exponer los parámetro que se pueden ver
//	    p.setCity(entity.getCity());
//	    p.setDate(entity.getDate());
//	    p.setWhoDefends(entity.getWhoDefends());
	    BeanUtils.copyProperties(entity, p);
	    protestList.add(p);
	});
	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

    public Protest getProtest(long id) {
	ProtestEntity protestEntity = repository.findById(id);
	Protest protest = new Protest();
	BeanUtils.copyProperties(protestEntity, protest);
	return protest;
    }

    public void deleteProtest(long id) {
	repository.deleteById(id);
    }

    public Protests searchProtest(String city) {
	List<Protest> protestList = new ArrayList<Protest>();
	repository.findByCity(city).forEach(entity -> {
	    Protest p = new Protest();
	    BeanUtils.copyProperties(entity, p);
	    protestList.add(p);
	});
	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

}
