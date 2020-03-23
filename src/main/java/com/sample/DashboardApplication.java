package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.sample.aspect.MetricAspect;
import com.sample.config.ConfigProperties;

/**
 * @author Mahnaz
 * @Jan 31, 2020
 */

@SpringBootApplication
@EnableAspectJAutoProxy	
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
@EnableDiscoveryClient
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Bean
	MetricAspect statisticAspect(){
		return new MetricAspect();
	}
}
