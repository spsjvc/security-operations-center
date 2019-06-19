package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class AntivirusThreatDetectionLog extends Log {

    public AntivirusThreatDetectionLog(Long id, LocalDateTime timestamp, Machine machine, String application) {
        super(id, timestamp, machine, application);
    }
}
