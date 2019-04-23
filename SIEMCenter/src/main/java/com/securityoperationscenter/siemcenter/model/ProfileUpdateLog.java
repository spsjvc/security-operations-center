package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class ProfileUpdateLog extends Log {

    private String username;

    public ProfileUpdateLog(Date timestamp, Machine machine, String application, String username) {
        super(timestamp, machine, application);

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
