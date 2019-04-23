package com.securityoperationscenter.siemcenter;

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
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RulesTests {

    @Autowired
    KieContainer kieContainer;

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
}
