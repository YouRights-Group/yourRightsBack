package com.yourrights.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yourrights.repository.beans.ProtestEntity;

public interface ProtestsRepository extends CrudRepository<ProtestEntity, Long> {

    ProtestEntity findById(long id);

    List<ProtestEntity> findAll();

    List<ProtestEntity> findByCity(String city);

    List<ProtestEntity> findByCityAndWhoDefendsAndDate(String city, String whoDefends, Date date);

    List<ProtestEntity> findByCityAndDateAndTime(String city, Date date, Date time);
}
