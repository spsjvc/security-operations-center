package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class AntivirusThreatEliminationLog extends Log {

    public AntivirusThreatEliminationLog(Long id, LocalDateTime timestamp, Machine machine, String application) {
        super(id, timestamp, machine, application);
    }
}
