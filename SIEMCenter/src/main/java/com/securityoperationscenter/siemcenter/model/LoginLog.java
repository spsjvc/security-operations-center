package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class LoginLog extends Log {

    private String username;
    private boolean isSuccessful;

    public LoginLog(Date timestamp, Machine machine, String application, String username, boolean isSuccessful) {
        super(timestamp, machine, application);

        this.username = username;
        this.isSuccessful = isSuccessful;
    }
}
