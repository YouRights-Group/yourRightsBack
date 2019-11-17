package com.yourrights.services;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yourrights.beans.ConfigProperties;
import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.beans.SearchRequest;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.ProtestsException;
import com.yourrights.repository.LocationRepository;
import com.yourrights.repository.ProtestsRepository;
import com.yourrights.repository.beans.LocationEntity;
import com.yourrights.repository.beans.ProtestEntity;
import com.yourrights.repository.beans.UserEntity;

@Service
public class ProtestsService {

    @Autowired
    private ProtestsRepository protestsRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ConfigProperties properties;

    public void createProtest(Protest protest) {

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	Map<String, Object> info = (Map<String, Object>) ((AbstractAuthenticationToken) auth).getDetails();
	UserEntity userEntity = (UserEntity) info.get("user");

	if (validateProtest(protest)) {
	    ProtestEntity protestEntity = new ProtestEntity();
	    BeanUtils.copyProperties(protest, protestEntity);
	    protestEntity.setProtestType(protest.getProtestType().name());
	    protestEntity.setUserType(protest.getUserType().name());
	    Set<LocationEntity> locationsProtest = new HashSet<LocationEntity>();
	    for (int i = 0; i < protest.getLocationsProtest().size(); i++) {
		LocationEntity locEntity = new LocationEntity();
		BeanUtils.copyProperties(protest.getLocationsProtest().get(0), locEntity);
		locEntity = checkExistedLocation(locEntity);
		locEntity.setPointNumber(i);
		locationsProtest.add(locEntity);
	    }
	    protestEntity.setLocationsProtest(locationsProtest);
	    protestEntity.setUserId(userEntity.getId());

	    protestsRepository.save(protestEntity);
	}
    }

    private LocationEntity checkExistedLocation(LocationEntity locEntity) {
	List<LocationEntity> locationsExisted = locationRepository.findByLatitudeAndLongitude(locEntity.getLatitude(),
		locEntity.getLongitude());
	if (!locationsExisted.isEmpty()) {
	    locEntity = locationsExisted.get(0);
	}
	return locEntity;
    }

    private boolean validateProtest(Protest protest) {
	// Validation 1: Protest with same city, whoDefends and date.
	List<ProtestEntity> list = protestsRepository.findByCityAndDefenseSectorAndDate(protest.getCity(),
		protest.getDefenseSector(), protest.getDate());
	if (list.size() > 0)
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_001, "Protest very similar");
	// Validation 2: Protest at same time in same city
	list = protestsRepository.findByCityAndDate(protest.getCity(), protest.getDate());
	if (list.size() > 0)
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_002,
		    "Protest in the same city at the same time");
	return true;
    }

    public Protests getProtests(int pos) {
	List<Protest> protestList = new ArrayList<Protest>();
	Pageable pageable = PageRequest.of(pos + 0, pos + properties.getNumMaxPage());
	Page<ProtestEntity> page = protestsRepository.findAll(pageable);
	page.getContent().forEach(entity -> {
	    Protest p = new Protest();
	    p.setCity(entity.getCity());
	    p.setName(entity.getName());
	    p.setDate(entity.getDate());
	    p.setDefenseSector(entity.getDefenseSector());
	    p.setPromotedBy(entity.getPromotedBy());
	    p.setMonth(getMonth(entity.getDate()));
	    protestList.add(p);
	});

	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

    public Protest getProtest(long id) {
	ProtestEntity protestEntity = protestsRepository.findById(id);
	Protest protest = new Protest();
	BeanUtils.copyProperties(protestEntity, protest);
	return protest;
    }

    public void deleteProtest(long id) {
	protestsRepository.deleteById(id);
    }

    public Protests searchProtest(SearchRequest request) {
	List<Protest> protestList = new ArrayList<Protest>();
	protestsRepository.findByCityAndDefenseSectorAndDate(request.getCity(), null, null).forEach(entity -> {
	    Protest p = new Protest();
	    BeanUtils.copyProperties(entity, p);
	    protestList.add(p);
	});

	protestsRepository.findByCity(request.getCity()).forEach(entity -> {
	    Protest p = new Protest();
	    BeanUtils.copyProperties(entity, p);
	    protestList.add(p);
	});
	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

    private String getMonth(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	int month = calendar.get(Calendar.MONTH);
	return Month.values()[month - 1].name();
    }

}
