package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class ErrorLog extends Log {

    public ErrorLog(Date timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
