package com.codestates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@EnableJpaAuditing
@SpringBootApplication
public class Section3AdvancedSendEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(Section3AdvancedSendEmailApplication.class, args);
	}
}