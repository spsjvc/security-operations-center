package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class ProfileUpdateLog extends Log {

    private String username;

    public ProfileUpdateLog(Long id, LocalDateTime timestamp, Machine machine, String application, String username) {
        super(id, timestamp, machine, application);

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
