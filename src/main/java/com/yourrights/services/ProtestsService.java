package com.yourrights.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourrights.beans.Protest;
import com.yourrights.beans.Protests;
import com.yourrights.repository.ProtestsRepository;
import com.yourrights.repository.beans.ProtestEntity;

@Service
public class ProtestsService {

    @Autowired
    private ProtestsRepository repository;

    public void createProtest(Protest protest) {
        ProtestEntity protestEntity = new ProtestEntity();
        BeanUtils.copyProperties(protest, protestEntity);
        repository.save(protestEntity);

    }

    public Protests getProtests() {
    	List<Protest> protestList = new ArrayList<Protest>();
        repository.findAll().forEach(entity -> {
        	Protest p = new Protest();
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
