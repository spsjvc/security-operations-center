package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class AntivirusThreatEliminationLog extends Log {

    public AntivirusThreatEliminationLog(LocalDateTime timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
