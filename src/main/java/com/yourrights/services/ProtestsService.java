package com.yourrights.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.beans.SearchRequest;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.ProtestsException;
import com.yourrights.repository.ProtestsRepository;
import com.yourrights.repository.beans.LocationEntity;
import com.yourrights.repository.beans.ProtestEntity;

@Service
public class ProtestsService {

    @Autowired
    private ProtestsRepository repository;

    public void createProtest(Protest protest) {

	if (validateProtest(protest)) {
	    ProtestEntity protestEntity = new ProtestEntity();
	    BeanUtils.copyProperties(protest, protestEntity);
	    protestEntity.setProtestsType(protest.getProtestType().name());
	    protestEntity.setUserType(protest.getUserType().name());
	    Set<LocationEntity> locationsProtest = new HashSet<LocationEntity>();
	    protest.getLocationsProtest().forEach(loc -> {
		LocationEntity locEntity = new LocationEntity();
		BeanUtils.copyProperties(loc, locEntity);
		locEntity.setProtestId(protestEntity.getId());
		locationsProtest.add(locEntity);
	    });
	    protestEntity.setLocationsProtest(locationsProtest);

	    ProtestEntity result = repository.save(protestEntity);
	    System.out.println();
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

    public Protests getProtests(int pos) {
	List<Protest> protestList = new ArrayList<Protest>();
	Pageable pageable = PageRequest.of(pos + 0, pos + 20);
	Page<ProtestEntity> page = repository.findAll(pageable);
	page.getContent().forEach(entity -> {
	    Protest p = new Protest();
	    p.setCity(entity.getCity());
	    p.setName(entity.getName());
	    p.setDate(entity.getDate());
	    p.setWhoDefends(entity.getWhoDefends());
	    p.setPromotedBy(entity.getPromotedBy());
	    p.setMonth(getMonth(entity.getDate()));
	    protestList.add(p);
	});

	// TODO: por paginación
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

    public Protests searchProtest(SearchRequest request) {
	List<Protest> protestList = new ArrayList<Protest>();
	repository.findByCity(request.getCity()).forEach(entity -> {
	    Protest p = new Protest();
	    BeanUtils.copyProperties(entity, p);
	    protestList.add(p);
	});
	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

    private String getMonth(Date date) {
	LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	int month = localDate.getMonthValue();
	return Month.values()[month - 1].name();
    }

}
