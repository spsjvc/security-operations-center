package com.securityoperationscenter.siemcenter.model;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.time.LocalDateTime;

@Role(Role.Type.EVENT)
@Timestamp("timestamp")
public class PaymentLog extends Log {

    private String username;

    public PaymentLog(LocalDateTime timestamp, Machine machine, String application, String username) {
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
