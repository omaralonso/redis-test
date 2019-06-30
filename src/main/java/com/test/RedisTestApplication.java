package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisTestApplication {

	private static Logger logger = LoggerFactory.getLogger(RedisTestApplication.class);
	
	public static void main(String[] args) {
		logger.info("INICIO APP");
		SpringApplication.run(RedisTestApplication.class, args);
	}

}
