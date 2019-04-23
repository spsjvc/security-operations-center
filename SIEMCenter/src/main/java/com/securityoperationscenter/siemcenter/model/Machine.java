package com.securityoperationscenter.siemcenter.model;

public class Machine {

    private String ip;
    private String operatingSystem;

    public Machine(String ip, String operatingSystem) {
        this.ip = ip;
        this.operatingSystem = operatingSystem;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
