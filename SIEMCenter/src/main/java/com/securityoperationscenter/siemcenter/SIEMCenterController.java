package com.securityoperationscenter.siemcenter;

import com.securityoperationscenter.siemcenter.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SIEMCenterController {

    private static Logger log = LoggerFactory.getLogger(SIEMCenterController.class);

    private final SIEMCenterService siemCenterService;

    @Autowired
    public SIEMCenterController(SIEMCenterService siemCenterService) {
        this.siemCenterService = siemCenterService;
    }

    @RequestMapping(value="/simulate-payment-system-alarm", method=RequestMethod.POST)
    public ResponseEntity<Void> simulatePaymentSystemAlarm() {
        siemCenterService.simulatePaymentSystemAlarm();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value="/simulate-three-unsuccessful-logins-alarm", method=RequestMethod.POST)
    public ResponseEntity<Void> simulateThreeUnsuccessfulLoginsAlarm() {
        siemCenterService.simulateThreeUnsuccessfulLoginsAlarm();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value="/simulate-seven-antivirus-threats-from-the-same-machine", method=RequestMethod.POST)
    public ResponseEntity<Void> simulateSevenAntivirusThreatsFromTheSameMachine() {
        siemCenterService.simulateSevenAntivirusThreatsFromTheSameMachine();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value="/simulate-login-from-inactive-account", method=RequestMethod.POST)
    public ResponseEntity<Void> simulateLoginFromInactiveAccount() {
        siemCenterService.simulateLoginFromInactiveAccount();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value="/simulate-fifteen-logins-from-the-same-ip", method=RequestMethod.POST)
    public ResponseEntity<Void> simulateFifteenUnsuccessfulLoginsFromTheSameIP() {
        siemCenterService.simulateFifteenUnsuccessfulLoginsFromTheSameIP();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
