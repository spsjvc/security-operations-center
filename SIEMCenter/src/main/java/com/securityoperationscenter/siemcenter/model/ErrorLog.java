package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class ErrorLog extends Log {

    public ErrorLog(Long id, LocalDateTime timestamp, Machine machine, String application) {
        super(id, timestamp, machine, application);
    }
}
