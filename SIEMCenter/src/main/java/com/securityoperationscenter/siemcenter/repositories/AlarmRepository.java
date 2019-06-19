package com.securityoperationscenter.siemcenter.repositories;

import com.securityoperationscenter.siemcenter.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}

