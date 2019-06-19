package com.securityoperationscenter.siemcenter.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class AntivirusThreatDetectionLog extends Log {

    public AntivirusThreatDetectionLog(LocalDateTime timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
