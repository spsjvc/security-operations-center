package com.securityoperationscenter.siemcenter.model;

import java.time.LocalDateTime;

public class PaymentLog extends Log {

    public PaymentLog(LocalDateTime timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
