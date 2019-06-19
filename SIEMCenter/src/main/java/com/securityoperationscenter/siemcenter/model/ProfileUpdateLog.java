package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class ProfileUpdateLog extends Log {

    private String username;

    public ProfileUpdateLog(LocalDateTime timestamp, Machine machine, String application, String username) {
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
