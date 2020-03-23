package com.sample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class read configs from application.properties file. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */


@Configuration
@ConfigurationProperties(prefix = "customer")
public class ConfigProperties { 

	private String dataServiceURI;
	private String measureServiceURI;
	private String serviceURI;

	public String getDataServiceURI() {
		return dataServiceURI;
	}
	public void setDataServiceURI(String dataServiceURI) {
		this.dataServiceURI = dataServiceURI;
	}
	public String getMeasureServiceURI() {
		return measureServiceURI;
	}
	public void setMeasureServiceURI(String measureServiceURI) {
		this.measureServiceURI = measureServiceURI;
	}
	public String getServiceURI() {
		return serviceURI;
	}
	public void setServiceURI(String serviceURI) {
		this.serviceURI = serviceURI;
	}
}
