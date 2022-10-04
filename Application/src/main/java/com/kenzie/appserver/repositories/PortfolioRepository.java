package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PortfolioRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PortfolioRepository extends CrudRepository<PortfolioRecord, String> {
    PortfolioRecord findByUserId(String userId);

}
