package com.yourrights.services;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yourrights.beans.ConfigProperties;
import com.yourrights.beans.Location;
import com.yourrights.beans.Protest;
import com.yourrights.beans.ProtestType;
import com.yourrights.beans.Protests;
import com.yourrights.beans.SearchRequest;
import com.yourrights.beans.UserType;
import com.yourrights.constants.Constants;
import com.yourrights.exceptions.ProtestsException;
import com.yourrights.repository.HistoricProtestsRepository;
import com.yourrights.repository.LocationRepository;
import com.yourrights.repository.ProtestsLocationsRepository;
import com.yourrights.repository.ProtestsRepository;
import com.yourrights.repository.beans.HistoricProtestEntity;
import com.yourrights.repository.beans.LocationEntity;
import com.yourrights.repository.beans.ProtestEntity;
import com.yourrights.repository.beans.ProtestLocationEntity;
import com.yourrights.repository.beans.UserEntity;
import com.yourrights.repository.specifications.ProtestsSpecification;
import com.yourrights.repository.specifications.SearchCriteria;
import com.yourrights.utils.SecurityContextUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProtestsService {

    @Autowired
    private ProtestsRepository protestsRepository;
    @Autowired
    private HistoricProtestsRepository historicProtestsRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ProtestsLocationsRepository protestsLocationsRepository;

    @Autowired
    private ConfigProperties properties;

    public void changeProtest(Protest newProtest) {
	ProtestEntity protestEntity = protestsRepository.findById(newProtest.getId());
	BeanUtils.copyProperties(newProtest, protestEntity);
	protestsRepository.save(protestEntity);
    }

    private LocationEntity checkExistedLocation(LocationEntity locEntity) {
	List<LocationEntity> locationsExisted = locationRepository.findByLatitudeAndLongitude(locEntity.getLatitude(),
		locEntity.getLongitude());
	if (!locationsExisted.isEmpty()) {
	    locEntity = locationsExisted.get(0);
	} else {
	    locEntity = locationRepository.save(locEntity);
	}
	return locEntity;
    }

    public void createProtest(Protest protest) {

	UserEntity userEntity = SecurityContextUtils.getUser();

	if (validateProtest(protest)) {

	    ProtestEntity protestEntity = new ProtestEntity();
	    BeanUtils.copyProperties(protest, protestEntity);
	    protestEntity.setProtestType(protest.getProtestType().name());
	    protestEntity.setUserType(protest.getUserType().name());
	    protestEntity.setMonth(getMonth(protest.getDate()));
	    protestEntity.setUserId(userEntity.getId());
	    protestEntity = protestsRepository.save(protestEntity);

	    Set<LocationEntity> locationsProtest = new HashSet<LocationEntity>();
	    List<ProtestLocationEntity> protestLocationEntityList = new ArrayList<ProtestLocationEntity>();
	    for (int i = 0; i < protest.getLocationsProtest().size(); i++) {
		LocationEntity locEntity = new LocationEntity();
		BeanUtils.copyProperties(protest.getLocationsProtest().get(i), locEntity);
		locEntity = checkExistedLocation(locEntity);
		locationsProtest.add(locEntity);

		ProtestLocationEntity protestLocationEntity = new ProtestLocationEntity();
		protestLocationEntity.setLocation(locEntity);
		protestLocationEntity.setProtest(protestEntity);
		protestLocationEntity.setIndex(i);
		protestLocationEntityList.add(protestLocationEntity);
	    }

	    protestsLocationsRepository.saveAll(protestLocationEntityList);
	}
    }

    public void deleteProtest(long id) {
	ProtestEntity protest = protestsRepository.findById(id);
	HistoricProtestEntity historicProtest = new HistoricProtestEntity();
	BeanUtils.copyProperties(protest, historicProtest);

	historicProtestsRepository.save(historicProtest);
	protestsRepository.deleteById(id);
    }

    private String getMonth(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	int month = calendar.get(Calendar.MONTH);
	return Month.values()[month - 1].name();
    }

    public Protest getProtest(long id) {
	ProtestEntity protestEntity = protestsRepository.findById(id);
	Protest protest = new Protest();
	BeanUtils.copyProperties(protestEntity, protest);

	// Obtener los meses
	protest.setProtestType(ProtestType.valueOf(protestEntity.getProtestType()));
	protest.setUserType(UserType.valueOf(protestEntity.getUserType()));

	List<ProtestLocationEntity> locationsEntityList = protestsLocationsRepository.findByProtestId(id);
	List<Location> locations = new ArrayList<Location>();
	for (ProtestLocationEntity protestLocationEntity : locationsEntityList) {
	    LocationEntity locationEntity = protestLocationEntity.getLocation();
	    Location location = new Location();
	    BeanUtils.copyProperties(locationEntity, location);
	    location.setPointNumber(protestLocationEntity.getIndex());
	    locations.add(location);
	}
	protest.setLocationsProtest(locations);
	return protest;
    }

    public Protests getProtests(int pageIndex) {
	List<Protest> protestList = new ArrayList<Protest>();
	Pageable pageable = PageRequest.of(pageIndex, pageIndex + properties.getMaxNumPage());
	Page<ProtestEntity> page = protestsRepository.findAll(pageable);
	page.getContent().forEach(entity -> {
	    Protest p = new Protest();
	    p.setCity(entity.getCity());
	    p.setName(entity.getName());
	    p.setDate(entity.getDate());
	    p.setDefenseSector(entity.getDefenseSector());
	    p.setPromotedBy(entity.getPromotedBy());
	    p.setMonth(getMonth(entity.getDate()));
	    p.setId(entity.getId());
	    protestList.add(p);
	});

	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;
    }

    public void loadDocument(MultipartFile file, long id) {
	byte[] document = null;
	if (!file.isEmpty()) {
	    try {
		document = file.getBytes();
	    } catch (IOException e) {
		log.error("Error to read document");
	    }
	}

	ProtestEntity protestEntity = protestsRepository.findById(id);
	protestEntity.setDocument(document);
	protestsRepository.save(protestEntity);
    }

    public Protests searchProtest(SearchRequest request) {

	ProtestsSpecification specCity = new ProtestsSpecification();
	if (!StringUtils.isEmpty(request.getCityProtest())) {
	    specCity = new ProtestsSpecification(new SearchCriteria("city", ":", request.getCityProtest()));
	}
	ProtestsSpecification specDate = new ProtestsSpecification();
	if (request.getFromProtest() != null && request.getToProtest() != null) {
	    specDate = new ProtestsSpecification(
		    new SearchCriteria("date", "compare", request.getFromProtest(), request.getToProtest()));
	}
	ProtestsSpecification specArea = new ProtestsSpecification();
	if (request.getAreaProtest() != null) {
	    specArea = new ProtestsSpecification(new SearchCriteria("area", ":", request.getAreaProtest()));
	}
	ProtestsSpecification specCountry = new ProtestsSpecification();
	if (request.getCountryProtest() != null) {
	    specCountry = new ProtestsSpecification(new SearchCriteria("country", ":", request.getCountryProtest()));
	}
	ProtestsSpecification specMonth = new ProtestsSpecification();
	if (request.getMonthProtest() != null) {
	    specMonth = new ProtestsSpecification(new SearchCriteria("month", ":", request.getMonthProtest()));
	}

	List<Protest> protestList = new ArrayList<Protest>();
	int pageIndex = request.getPageIndex();
	Pageable pageable = PageRequest.of(pageIndex, pageIndex + properties.getMaxNumPage());
	protestsRepository
		.findAll(Specification.where(specCity).and(specDate).and(specArea).and(specCountry).and(specMonth),
			pageable)
		.forEach(entity -> {
		    Protest p = new Protest();
		    p.setCity(entity.getCity());
		    p.setName(entity.getName());
		    p.setDate(entity.getDate());
		    p.setDefenseSector(entity.getDefenseSector());
		    p.setPromotedBy(entity.getPromotedBy());
		    p.setMonth(getMonth(entity.getDate()));
		    p.setId(entity.getId());
		    protestList.add(p);
		});

	Protests protests = new Protests();
	protests.setProtests(protestList);
	return protests;

    }

    private boolean validateProtest(Protest protest) {
	// Validation 1: Protest with same city, whoDefends and date.
	List<ProtestEntity> list = protestsRepository.findByCityAndDefenseSectorAndDate(protest.getCity(),
		protest.getDefenseSector(), protest.getDate());
	if (list.size() > 0) {
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_001, "Protest very similar");
	}
	// Validation 2: Protest at same time in same city
	list = protestsRepository.findByCityAndDate(protest.getCity(), protest.getDate());
	if (list.size() > 0) {
	    throw new ProtestsException(Constants.WARNING, Constants.WAR_002,
		    "Protest in the same city at the same time");
	}
	return true;
    }

}
