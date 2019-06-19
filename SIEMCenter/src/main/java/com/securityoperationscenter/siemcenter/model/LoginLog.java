package com.securityoperationscenter.siemcenter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class LoginLog extends Log {

    @Column
    private String username;

    @Column
    private boolean successful;

    public LoginLog() {}

    public LoginLog(LocalDateTime timestamp, Machine machine, String application, String username, boolean successful) {
        super(timestamp, machine, application);

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
