package com.software.TALL.TATHeaderChecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.software.TALL")
@SpringBootApplication
public class Process {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Process.class, args);
	}

}
