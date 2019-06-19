package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class LoginLog extends Log {

    private String username;
    private boolean successful;

    public LoginLog(Long id, LocalDateTime timestamp, Machine machine, String application, String username, boolean successful) {
        super(id, timestamp, machine, application);

        this.username = username;
        this.successful = successful;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
