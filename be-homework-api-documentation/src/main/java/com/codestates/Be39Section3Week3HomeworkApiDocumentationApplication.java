package com.codestates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@EnableJpaAuditing
@SpringBootApplication
public class Be39Section3Week3HomeworkApiDocumentationApplication {

	public static void main(String[] args) {
		SpringApplication.run(Be39Section3Week3HomeworkApiDocumentationApplication.class, args);

	}

}
