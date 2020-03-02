package com.smoothstack.lms.adminmicroservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.smoothstack.lms")
@EnableTransactionManagement
public class CustomConfiguration {

	
}