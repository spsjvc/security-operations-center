package com.securityoperationscenter.siemcenter.dto;

import com.securityoperationscenter.siemcenter.model.Alarm;

import java.time.LocalDateTime;

public class AlarmDTO {

    private Long id;

    private String type;

    private String message;

    private String username;

    private LocalDateTime timestamp;

    public AlarmDTO(Alarm alarm) {
        this.id = alarm.getId();
        this.type = alarm.getType();
        this.message = alarm.getMessage();
        this.username = alarm.getUsername();
        this.timestamp = alarm.getTimestamp();
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
