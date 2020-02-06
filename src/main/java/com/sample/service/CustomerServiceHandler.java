package com.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
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
 * This service calls customer services. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Service
@Api(value = "Customer Service Handler")
public class CustomerServiceHandler {

	@Autowired
	private ConfigProperties configProperties;

	/**
	 * Call customer service to get dimensions 
	 * @param customerDataRequest
	 * @return ResponseEntity<CustomerDimensionResponse>
	 */
	@Metric
	public ResponseEntity<CustomerDimensionResponse> callCustomerDataService(CustomerDimensionRequest customerDataRequest) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(configProperties.getDataServiceURI(),
				customerDataRequest, CustomerDimensionResponse.class);
	}

	/**
	 * Call customer service to get measures
	 * @param customerMeasureRequest
	 * @return ResponseEntity<CustomerMeasureResponse>
	 */
	@Metric
	public ResponseEntity<CustomerMeasureResponse> callCustomerMeasureService(CustomerMeasureRequest customerMeasureRequest) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(configProperties.getMeasureServiceURI(),
				customerMeasureRequest, CustomerMeasureResponse.class);
	}
}
