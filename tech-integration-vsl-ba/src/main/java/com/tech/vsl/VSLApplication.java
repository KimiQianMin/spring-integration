package com.tech.vsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableEurekaClient
@Configuration
public class VSLApplication {

	public static void main(String[] args) {
		SpringApplication.run(VSLApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

		RedisSerializer stringSerializer = new StringRedisSerializer();

		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

}
