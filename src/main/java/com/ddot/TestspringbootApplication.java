package com.ddot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ddot.dao")
public class TestspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestspringbootApplication.class, args);
	}

}
