package com.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sample.aspect.Metric;
import com.sample.config.ConfigProperties;
import com.sample.dto.CustomerDimensionRequest;
import com.sample.dto.CustomerDimensionResponse;
import com.sample.dto.CustomerMeasureRequest;
import com.sample.dto.CustomerMeasureResponse;

import io.swagger.annotations.Api;

/**
 * This service calls customer services by using Ribbon load balancer.
 * Customer and Dashboard microservices are registered in Eureka server as a discovery server
 * and this server provides the information of microservices for the load balancer. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Service
@Api(value = "Customer Service Handler")
public class CustomerServiceHandler {

	@Autowired
	private ConfigProperties configProperties;

	@Autowired
	private LoadBalancerClient loadBalancer;

	/**
	 * Call customer service to get dimensions 
	 * @param customerDataRequest
	 * @return ResponseEntity<CustomerDimensionResponse>
	 */
	@Metric
	public ResponseEntity<CustomerDimensionResponse> callCustomerDataService(CustomerDimensionRequest customerDataRequest) {

		ServiceInstance servInstance= loadBalancer.choose(configProperties.getServiceURI());

		String baseUrl= servInstance.getUri().toString();
		baseUrl= baseUrl + configProperties.getDataServiceURI();
		RestTemplate restTemplate= new RestTemplate();
		return restTemplate.postForEntity(baseUrl,
				customerDataRequest, CustomerDimensionResponse.class);
	}

	/**
	 * Call customer service to get measures
	 * @param customerMeasureRequest
	 * @return ResponseEntity<CustomerMeasureResponse>
	 */
	@Metric
	public ResponseEntity<CustomerMeasureResponse> callCustomerMeasureService(CustomerMeasureRequest customerMeasureRequest) {
		ServiceInstance servInstance= loadBalancer.choose(configProperties.getServiceURI());

		String baseUrl= servInstance.getUri().toString();
		baseUrl= baseUrl + configProperties.getMeasureServiceURI();

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(baseUrl,
				customerMeasureRequest, CustomerMeasureResponse.class);
	}
}
