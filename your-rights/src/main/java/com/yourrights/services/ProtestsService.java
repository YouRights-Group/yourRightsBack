package com.yourrights.services;

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
        // TODO Auto-generated method stub
        return null;
    }

}
