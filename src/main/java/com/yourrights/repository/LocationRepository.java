package com.yourrights.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yourrights.repository.beans.LocationEntity;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

    List<LocationEntity> findByLatitudeAndLongitude(double latitude, double longitude);
}
