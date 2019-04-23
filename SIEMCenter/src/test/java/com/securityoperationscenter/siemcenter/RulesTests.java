package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.model.AntivirusThreatDetectionLog;
import com.securityoperationscenter.siemcenter.model.ErrorLog;
import com.securityoperationscenter.siemcenter.model.LoginLog;
import com.securityoperationscenter.siemcenter.model.Machine;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RulesTests {

    @Autowired
    KieContainer kieContainer;

    @Test
    public void detectWhenErrorLogAppears() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Detect when ErrorLog appears");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        Machine testMachine = new Machine("0.0.0.0", "Windows");

        ErrorLog errorLog = new ErrorLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication"
        );

        kieSession.insert(errorLog);
        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }

    @Test
    public void threeUnsuccessfulLoginsWithTheSameUsername() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Three unsuccessful logins with the same username");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        Machine testMachine = new Machine("0.0.0.0", "Windows");

        LoginLog loginLog = new LoginLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication",
            "testUsername",
            true
        );

        kieSession.insert(loginLog);
        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();
        loginLog = new LoginLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication",
            "testUsername",
            false
        );
        kieSession.insert(loginLog);

        loginLog = new LoginLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication",
            "testUsername",
            false
        );
        kieSession.insert(loginLog);

        loginLog = new LoginLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication",
            "testUsername",
            false
        );
        kieSession.insert(loginLog);

        firedRules = kieSession.fireAllRules(filter);

        Assert.assertEquals(3, firedRules);
        kieSession.dispose();
    }

    @Test
    public void fifteenUnsuccessfulLoginsFromTheSameIp() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Fifteen unsuccessful logins from the same ip in five days");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        LoginLog loginLog;

        Machine testMachine1 = new Machine("0.0.0.0", "Windows");
        Machine testMachine2 = new Machine("0.0.0.1", "Windows");

        for (int i = 0; i < 10; i++) {
            loginLog = new LoginLog(
                LocalDateTime.now().plusMinutes(i),
                testMachine1,
                "testApplication",
                "testUsername",
                false
            );

            kieSession.insert(loginLog);
        }

        for (int i = 0; i < 10; i++) {
            loginLog = new LoginLog(
                    LocalDateTime.now().plusMinutes(i),
                    testMachine2,
                    "testApplication",
                    "testUsername",
                    false
            );

            kieSession.insert(loginLog);
        }

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();

        for (int i = 0; i < 15; i++) {
            loginLog = new LoginLog(
                LocalDateTime.now().plusMinutes(i),
                testMachine1,
                "testApplication",
                "testUsername",
                false
            );

            kieSession.insert(loginLog);
        }

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);

        kieSession.dispose();
    }

    @Test
    public void loginFromAccountInactiveFor90Days() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter( "Login from account inactive for 90 days");
        kieSession.fireAllRules(filter);
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        Machine testMachine = new Machine("0.0.0.0", "Windows");

        LoginLog loginLog = new LoginLog(
            LocalDateTime.now(),
            testMachine,
            "testApplication",
            "testUsername",
            true
        );
        kieSession.insert(loginLog);

        loginLog = new LoginLog(
            LocalDateTime.now().plusDays(20),
            testMachine,
            "testApplication",
            "testUsername",
            true
        );
        kieSession.insert(loginLog);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();

        loginLog = new LoginLog(
            LocalDateTime.now().minusDays(100),
            testMachine,
            "testApplication",
            "testUsername",
            true
        );
        kieSession.insert(loginLog);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }

    @Test
    public void sevenAntivirusThreatsFromSameMachine() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter( "Seven antivirus threats from same machine");
        kieSession.fireAllRules(filter);
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        Machine machine1 = new Machine("0.0.0.0", "Windows");
        Machine machine2 = new Machine("0.0.0.1", "Windows");

        AntivirusThreatDetectionLog log;

        for (int i = 0; i < 5; i++) {
            log = new AntivirusThreatDetectionLog(
                LocalDateTime.now(),
                machine1,
                "testApplication"
            );
            kieSession.insert(log);
        }

        for (int i = 0; i < 5; i++) {
            log = new AntivirusThreatDetectionLog(
                LocalDateTime.now(),
                machine2,
                "testApplication"
            );
            kieSession.insert(log);
        }

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();

        for (int i = 0; i < 7; i++) {
            log = new AntivirusThreatDetectionLog(
                LocalDateTime.now(),
                machine1,
                "testApplication"
            );
            kieSession.insert(log);
        }

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }
}
