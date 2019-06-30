package com.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {
	@Value("${redis.endpoint}")
	public String redisEndpoint;

	@Value("${redis.port}")
	public int redisPort;
}