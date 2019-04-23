package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public abstract class Log {

    protected LocalDateTime timestamp;
    protected Machine machine;
    protected String application;

    public Log(LocalDateTime timestamp, Machine machine, String application) {
        this.timestamp = timestamp;
        this.machine = machine;
        this.application = application;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
