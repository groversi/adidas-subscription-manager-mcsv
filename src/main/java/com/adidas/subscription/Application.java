package com.adidas.subscription;

import com.adidas.subscription.exceptions.FeignErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@ComponentScan("com.adidas.subscription")
@EnableConfigurationProperties
@EnableFeignClients
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@Primary
	public FeignErrorDecoder errorDecoder(){return new FeignErrorDecoder();}
}
