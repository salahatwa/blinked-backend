package com.blinked;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api.common.EnableCommonApi;

@EnableCommonApi
@SpringBootApplication
public class BlinkedApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlinkedApplication.class, args);
	}

}
