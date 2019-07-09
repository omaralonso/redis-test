package com.test.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport implements CachingConfigurer {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.cache.redis.time-to-live}")
	private long timeToLive;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofMillis(timeout)).build();
		final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
		    .commandTimeout(Duration.ofMillis(timeout)).clientOptions(clientOptions).build();

		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(host);
		redisConf.setPort(port);
		redisConf.setPassword(RedisPassword.of(password));

		final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConf, clientConfig);
		lettuceConnectionFactory.setValidateConnection(true);
		return new LettuceConnectionFactory(redisConf, clientConfig);

	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		    .entryTtl(Duration.ofMillis(timeToLive)).disableCachingNullValues();
		return cacheConfig;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager cacheManager() {
		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
		    .transactionAware().build();
		return rcm;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new RedisCacheErrorHandler();
	}
}