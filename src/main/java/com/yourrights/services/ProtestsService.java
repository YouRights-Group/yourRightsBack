package com.yourrights.services;

import java.util.ArrayList;
import java.util.List;

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
        protestEntity.setName(protest.getName());
        protestEntity.setCity(protest.getCity());
        repository.save(protestEntity);

    }

    public Protests getProtests() {
    	List<Protest> protestList = new ArrayList<Protest>();
        repository.findAll().forEach(entity -> {
        	Protest p = new Protest();
        	p.setName(entity.getName());
        	p.setCity(entity.getCity());
        	p.setId(entity.getId());
        	protestList.add(p);
        	});
        Protests protests = new Protests();
        protests.setProtests(protestList);
        return protests;
    }

	public Protest getProtest(long id) {
		ProtestEntity protestEntity = repository.findById(id);
		Protest protest = new Protest();
		protest.setName(protestEntity.getName());
		protest.setCity(protestEntity.getCity());
		protest.setId(protestEntity.getId());
		return protest;
	}

	public void deleteProtest(long id) {
		repository.deleteById(id);
	}

	public Protests searchProtest(String city) {
		List<Protest> protestList = new ArrayList<Protest>();
        repository.findByCity(city).forEach(entity -> {
        	Protest p = new Protest();
        	p.setName(entity.getName());
        	p.setCity(entity.getCity());
        	p.setId(entity.getId());
        	protestList.add(p);
        	});
        Protests protests = new Protests();
        protests.setProtests(protestList);
        return protests;
	}

}
