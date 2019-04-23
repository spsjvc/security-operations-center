package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class AntivirusThreatDetectionLog extends Log {

    public AntivirusThreatDetectionLog(Date timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
