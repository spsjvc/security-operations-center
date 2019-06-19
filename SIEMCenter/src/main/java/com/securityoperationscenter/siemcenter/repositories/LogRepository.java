package com.securityoperationscenter.siemcenter.repositories;

import com.securityoperationscenter.siemcenter.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}

