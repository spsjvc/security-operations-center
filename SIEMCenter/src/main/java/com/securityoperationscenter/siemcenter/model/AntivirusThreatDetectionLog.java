package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class AntivirusThreatDetectionLog extends Log {

    public AntivirusThreatDetectionLog(LocalDateTime timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
