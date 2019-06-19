package com.securityoperationscenter.siemcenter.model;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Role(Role.Type.EVENT)
public class PaymentLog extends Log {

    @Column
    private String username;

    public PaymentLog() {}

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
