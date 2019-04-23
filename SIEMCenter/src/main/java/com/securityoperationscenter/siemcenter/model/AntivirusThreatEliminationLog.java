package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class AntivirusThreatEliminationLog extends Log {

    public AntivirusThreatEliminationLog(Date timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
