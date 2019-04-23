package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class ErrorLog extends Log {

    public ErrorLog(LocalDateTime timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
