package com.securityoperationscenter.siemcenter.model;

public class Account {

    private String username;
    private String password;
    private AccountType type;
    private RiskLevel riskLevel;

    public Account(String username, String password, AccountType type, RiskLevel riskLevel) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.riskLevel = riskLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
}
