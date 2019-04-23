package com.securityoperationscenter.siemcenter.model;

import java.util.Date;

public class PaymentLog extends Log {

    public PaymentLog(Date timestamp, Machine machine, String application) {
        super(timestamp, machine, application);
    }
}
