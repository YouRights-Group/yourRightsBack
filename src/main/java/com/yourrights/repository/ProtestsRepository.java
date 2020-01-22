package com.yourrights.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yourrights.repository.beans.ProtestEntity;

@Repository
public interface ProtestsRepository
	extends CrudRepository<ProtestEntity, Long>, JpaSpecificationExecutor<ProtestEntity> {

    ProtestEntity findById(long id);

    Page<ProtestEntity> findAll(Pageable pageable);

    List<ProtestEntity> findByCity(String city);

    List<ProtestEntity> findByCityAndDefenseSectorAndDate(String city, String defenseSector, Date date);

    List<ProtestEntity> findByCityAndDate(String city, Date date);
}
