package com.yourrights.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.yourrights.repository.beans.ProtestEntity;

public interface ProtestsRepository extends CrudRepository<ProtestEntity, Long> {

    ProtestEntity findById(long id);

    Page<ProtestEntity> findAll(Pageable pageable);

    List<ProtestEntity> findByCity(String city);

    List<ProtestEntity> findByCityAndWhoDefendsAndDate(String city, String whoDefends, Date date);

    List<ProtestEntity> findByCityAndDateAndTime(String city, Date date, Date time);
}
