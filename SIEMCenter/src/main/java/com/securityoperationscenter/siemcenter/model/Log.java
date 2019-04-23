package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public abstract class Log {

    protected Date timestamp;
    protected Machine machine;
    protected String application;

    public Log(Date timestamp, Machine machine, String application) {
        this.timestamp = timestamp;
        this.machine = machine;
        this.application = application;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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
