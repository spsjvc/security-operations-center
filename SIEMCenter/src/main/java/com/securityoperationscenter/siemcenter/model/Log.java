package com.securityoperationscenter.siemcenter.model;

import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public abstract class Log {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column
    protected LocalDateTime timestamp;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    protected Machine machine;

    @Column
    protected String application;

    public Log(LocalDateTime timestamp, Machine machine, String application) {
        this.timestamp = timestamp;
        this.machine = machine;
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
