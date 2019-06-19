package com.securityoperationscenter.siemcenter.model;

import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Role(Role.Type.EVENT)
public class Alarm {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column
    private String type;

    @Column
    private String message;

    @Column
    private String username;

    @Column
    private LocalDateTime timestamp;

    public Alarm() {}

    public Alarm(String type, String message, String username, LocalDateTime timestamp) {
        this.type = type;
        this.message = message;
        this.username = username;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
