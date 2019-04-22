package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.model.Example;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExampleRuleTest {

    @Autowired
    KieContainer kieContainer;

    @Test
    public void example() {
        KieSession kieSession = kieContainer.newKieSession();
        AgendaFilter filter = new RuleNameEqualsAgendaFilter("Example rule");

        Example example = new Example("example");

        kieSession.insert(example);
        int firedRulesCount = kieSession.fireAllRules(filter);
        Assert.assertEquals(1, firedRulesCount);

        kieSession.dispose();
    }
}
