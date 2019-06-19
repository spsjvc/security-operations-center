package com.securityoperationscenter.siemcenter.repositories;

import com.securityoperationscenter.siemcenter.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

}

