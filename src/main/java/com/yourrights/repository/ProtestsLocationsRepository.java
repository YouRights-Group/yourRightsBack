package com.yourrights.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yourrights.repository.beans.ProtestLocationEntity;

@Repository
public interface ProtestsLocationsRepository extends CrudRepository<ProtestLocationEntity, Long> {

    List<ProtestLocationEntity> findByProtestId(long id);
}
