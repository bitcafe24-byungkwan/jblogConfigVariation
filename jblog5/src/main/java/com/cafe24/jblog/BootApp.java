package com.cafe24.jblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApp {
	public static void main(String[] args) {
		try {
			SpringApplication.run(BootApp.class, args);	
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
}
