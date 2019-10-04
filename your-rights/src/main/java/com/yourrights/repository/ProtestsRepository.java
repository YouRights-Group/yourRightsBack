package com.yourrights.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yourrights.repository.beans.ProtestEntity;

public interface ProtestsRepository extends CrudRepository<ProtestEntity, Long> {

    List<ProtestEntity> findById(long id);

    List<ProtestEntity> findAll();
}
