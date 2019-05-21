package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.model.*;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }

    @Test
    public void fifteenUnsuccessfulLoginsFromTheSameIp() {
        InputStream template = RulesTests.class.getResourceAsStream(
            "/siemcenterrules/rules/unsuccessful-logins-same-ip.drt"
        );

        DataProvider dataProvider = new ArrayDataProvider(
            new String[][]{
                new String[]{"15", "5"}
            }
        );

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        KieSession kieSession = createKieSessionFromDRL(drl);
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
        InputStream template = RulesTests.class.getResourceAsStream(
            "/siemcenterrules/rules/login-from-inactive-account.drt"
        );

        DataProvider dataProvider = new ArrayDataProvider(
            new String[][]{
                new String[]{"90"}
            }
        );

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        KieSession kieSession = createKieSessionFromDRL(drl);
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

        kieSession = createKieSessionFromDRL(drl);

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
        InputStream template = RulesTests.class.getResourceAsStream(
            "/siemcenterrules/rules/antivirus-threats-same-machine.drt"
        );

        DataProvider dataProvider = new ArrayDataProvider(
            new String[][]{
                new String[]{"7", "10"}
            }
        );

        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);

        KieSession kieSession = createKieSessionFromDRL(drl);
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

        kieSession = createKieSessionFromDRL(drl);

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

    @Test
    public void detectMaliciousIPAddress() {
        List<String> maliciousIpAddresses = new ArrayList<String>();
        maliciousIpAddresses.add("maliciousIP");

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("maliciousIpAddresses", maliciousIpAddresses);
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Detect malicious ip address");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("maliciousIpAddresses", maliciousIpAddresses);

        Machine okMachine = new Machine("okIP", "Windows");
        Machine maliciousMachine = new Machine("maliciousIP", "Windows");

        LoginLog log = new LoginLog(
            LocalDateTime.now(),
            okMachine,
            "testApplication",
            "testUsername",
            true
        );
        kieSession.insert(log);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("maliciousIpAddresses", maliciousIpAddresses);

        log = new LoginLog(
            LocalDateTime.now(),
            maliciousMachine,
            "testApplication",
            "testUsername",
            true
        );
        kieSession.insert(log);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }

    @Test
    public void firePaymentSystemAlarm() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Fire payment system alarm");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);

        LocalDateTime now = LocalDateTime.now();
        Machine machine = new Machine("0.0.0.0", "Windows");
        PaymentLog paymentLog;

        for (int i = 0; i < 51; i++) {
             paymentLog = new PaymentLog(
                now.minusSeconds(50 - i + 1),
                machine,
                "testApplication",
                "testUsername"
            );
            kieSession.insert(paymentLog);
        }

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        kieSession.dispose();
    }

    @Test
    public void setAccountRiskLevelToLow() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Set account risk level to low when no alarm in past 90 days");
        int firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();
        Account account = new Account(
            "username",
            "password",
            AccountType.USER,
            RiskLevel.MODERATE
        );
        kieSession.insert(account);

        Alarm alarm = new Alarm(
            "PAYMENT",
            "payment alarm",
            "username",
            LocalDateTime.now().minusDays(100)
        );
        kieSession.insert(alarm);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRules);
        Assert.assertEquals(account.getRiskLevel(), RiskLevel.LOW);
        kieSession.dispose();

        kieSession = kieContainer.newKieSession();
        account = new Account(
            "username",
            "password",
            AccountType.USER,
            RiskLevel.MODERATE
        );
        kieSession.insert(account);

        // alarm fired in past 90 days
        alarm = new Alarm(
            "PAYMENT",
            "payment alarm",
            "username",
            LocalDateTime.now().minusDays(40)
        );
        kieSession.insert(alarm);

        firedRules = kieSession.fireAllRules(filter);
        Assert.assertEquals(0, firedRules);
        Assert.assertEquals(account.getRiskLevel(), RiskLevel.MODERATE);
        kieSession.dispose();
    }

    private KieSession createKieSessionFromDRL(String drl) {
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);

            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        return kieHelper.build().newKieSession();
    }
}
