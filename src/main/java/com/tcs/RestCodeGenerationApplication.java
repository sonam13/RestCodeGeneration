package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.tcs")
public class RestCodeGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestCodeGenerationApplication.class, args);
	}
}
