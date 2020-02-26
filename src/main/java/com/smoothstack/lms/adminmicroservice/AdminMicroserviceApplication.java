package com.smoothstack.lms.adminmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smoothstack.lms.common")
@EntityScan(basePackages = "com.smoothstack.lms.common")
public class AdminMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminMicroserviceApplication.class, args);
	}

}
