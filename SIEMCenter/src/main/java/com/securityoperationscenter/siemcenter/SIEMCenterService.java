package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.dto.AlarmDTO;
import com.securityoperationscenter.siemcenter.model.*;
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
import java.util.ArrayList;
import java.util.List;

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
        Machine machine2 = new Machine(2L,"182.229.66.104", "macOS");
        machineRepository.save(machine);
        machineRepository.save(machine2);
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
                "username21242",
                false
            );
            logRepository.save(loginLog);
            kieSession.insert(loginLog);
        }

        this.kieSession.fireAllRules();
    }

    public void simulateSevenAntivirusThreatsFromTheSameMachine() {
        this.seed();

        LocalDateTime now = LocalDateTime.now();
        Machine machine = machineRepository.getOne(1L);

        AntivirusThreatDetectionLog log;

        for (int i = 0; i < 7; i++) {
            log = new AntivirusThreatDetectionLog(
                LocalDateTime.now().plusMinutes(i),
                machine,
            "TestApplication2"
            );
            logRepository.save(log);
            kieSession.insert(log);
        }

        this.kieSession.fireAllRules();
    }

    public void simulateLoginFromInactiveAccount() {
        this.seed();

        Machine machine = machineRepository.getOne(1L);

        LoginLog log = new LoginLog(
            LocalDateTime.now().minusDays(100),
            machine,
            "SomeApplication",
            "inactive_username",
            true
        );

        logRepository.save(log);
        kieSession.insert(log);
        this.kieSession.fireAllRules();
    }

    public void simulateFifteenUnsuccessfulLoginsFromTheSameIP() {
        this.seed();

        Machine machine = machineRepository.getOne(2L);

        LoginLog log;

        log = new LoginLog(
            LocalDateTime.now().minusDays(40),
            machine,
            "Application2",
            "randomUsername",
            false
        );

        logRepository.save(log);
        kieSession.insert(log);

        for (int i = 0; i < 15; i++) {
            log = new LoginLog(
                LocalDateTime.now().plusMinutes(i),
                machine,
                "Application2",
                "randomUsername" + i,
                false
            );

            logRepository.save(log);
            kieSession.insert(log);
        }

        this.kieSession.fireAllRules();
    }

    public List<AlarmDTO> getAlarms() {
        List<AlarmDTO> alarmsDTOs = new ArrayList<AlarmDTO>();
        ArrayList<Alarm> alarms = (ArrayList<Alarm>) alarmRepository.findAll();

        for (Alarm alarm : alarms) {
            alarmsDTOs.add(new AlarmDTO(alarm));
        }

        return alarmsDTOs;
    }
}
