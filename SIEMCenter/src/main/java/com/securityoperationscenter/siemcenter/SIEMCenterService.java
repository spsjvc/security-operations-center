package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.model.Alarm;
import com.securityoperationscenter.siemcenter.model.LoginLog;
import com.securityoperationscenter.siemcenter.model.Machine;
import com.securityoperationscenter.siemcenter.model.PaymentLog;
import com.securityoperationscenter.siemcenter.repositories.AlarmRepository;
import com.securityoperationscenter.siemcenter.repositories.LogRepository;
import com.securityoperationscenter.siemcenter.repositories.MachineRepository;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SIEMCenterService {

    private static Logger log = LoggerFactory.getLogger(SIEMCenterService.class);

    private final KieContainer kieContainer;
    private KieSession kieSession;

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    public SIEMCenterService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();

        this.kieSession.setGlobal("siemCenterService", this);
    }

    public void seed() {
        Machine machine = new Machine(1L,"0.0.0.0", "Windows");
        machineRepository.save(machine);
    }

    public void saveAlarm(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    public void simulatePaymentSystemAlarm() {
        this.seed();

        LocalDateTime now = LocalDateTime.now();
        Machine machine = machineRepository.getOne(1L);
        PaymentLog paymentLog;

        for (int i = 0; i < 50; i++) {
            paymentLog = new PaymentLog(
                now.minusSeconds(50 - i + 1),
                machine,
                "TestApplication",
                "username"
            );

            logRepository.save(paymentLog);
            kieSession.insert(paymentLog);
        }

        this.kieSession.fireAllRules();
    }

    public void simulateThreeUnsuccessfulLoginsAlarm() {
        this.seed();

        LocalDateTime now = LocalDateTime.now();
        Machine machine = machineRepository.getOne(1L);
        LoginLog loginLog;

        for (int i = 0; i < 3; i++) {
            loginLog = new LoginLog(
                LocalDateTime.now().plusSeconds(i),
                machine,
                "TestApplication",
                "username2",
                false
            );
            logRepository.save(loginLog);
            kieSession.insert(loginLog);
        }

        this.kieSession.fireAllRules();
    }
}
