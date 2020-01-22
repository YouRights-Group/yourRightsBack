package com.yourrights.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yourrights.repository.beans.HistoricProtestEntity;

@Repository
public interface HistoricProtestsRepository
	extends CrudRepository<HistoricProtestEntity, Long>, JpaSpecificationExecutor<HistoricProtestEntity> {

}
