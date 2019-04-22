package com.securityoperationscenter.siemcenter;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SIEMCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SIEMCenterApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices
			.newKieContainer(
				kieServices.newReleaseId(
				"com.securityoperationscenter",
				"siem-center-rules",
				"0.0.1-SNAPSHOT"
				)
			);
		KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
		kieScanner.start(10_000);
		return kieContainer;
	}
}
