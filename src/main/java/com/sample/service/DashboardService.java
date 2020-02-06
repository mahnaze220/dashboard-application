package com.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sample.dto.ChartRequest;
import com.sample.dto.ChartResponse;
import com.sample.dto.CustomerDimensionRequest;
import com.sample.dto.CustomerDimensionResponse;
import com.sample.dto.CustomerMeasureRequest;
import com.sample.dto.CustomerMeasureResponse;
import com.sample.dto.Measure;
import com.sample.exception.DashboardException;
import com.sample.exception.ExceptionType;

import io.swagger.annotations.Api;
import reactor.core.publisher.Mono;

/**
 * This service provides dashboard services. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Service
@Api(value = "Dashboard Service")
public class DashboardService {

	public static final String TEAM = "team";	

	@Autowired
	private CustomerServiceHandler customerServiceHandler;

	/**
	 * Get chart data
	 * @param chartRequest
	 * @return Mono<ChartResponse>
	 */
	public Mono<ChartResponse> getChartData(ChartRequest chartRequest) {
		if(chartRequest == null) {
			throw DashboardException.throwException(ExceptionType.INVALID_INPUT_DATA_EXCEPTION, 
					"Request can not be empty");
		}

		//validate chart request
		chartRequest.validate();

		ChartResponse chartResponse = new ChartResponse();		

		//first get categories of team dimension from customer service
		CustomerDimensionRequest customerDataRequest = new CustomerDimensionRequest();
		customerDataRequest.setDimensionName(TEAM);
		List<String> categories = getDimensionsFromCustomerService(customerDataRequest);

		//create a request for a query and call customer service 
		List<CustomerMeasureRequest> measureRequests = createCustomerDataRequest(chartRequest);
		List<CustomerMeasureResponse> responseList = getMeasureFromCustomerService(measureRequests);


		//create chart response
		List<Measure> measures = new ArrayList<>();
		responseList
		.stream()
		.forEach(res -> measures.add(new Measure(res.getName(), res.getData())));

		chartResponse.setCategories(categories);
		chartResponse.setSeries(measures);

		return Mono.just(chartResponse);	
	}

	public List<String> getDimensionsFromCustomerService(CustomerDimensionRequest customerDataRequest) {
		ResponseEntity<CustomerDimensionResponse> dataResponse = 
				customerServiceHandler.callCustomerDataService(customerDataRequest);
		return dataResponse.getBody().getData();
	}

	public List<CustomerMeasureResponse> getMeasureFromCustomerService(List<CustomerMeasureRequest> measureRequests) {
		List<CustomerMeasureResponse> responses = new ArrayList<>();
		for(CustomerMeasureRequest request: measureRequests) {
			CustomerMeasureRequest customerMeasureRequest = 
					new CustomerMeasureRequest(request.getMeasureName(), null);
			ResponseEntity<CustomerMeasureResponse> response = customerServiceHandler.callCustomerMeasureService(customerMeasureRequest);
			responses.add(response.getBody());
		}
		return responses;
	}

	public List<CustomerMeasureRequest> createCustomerDataRequest(ChartRequest chartRequest) {
		List<CustomerMeasureRequest> dataRequestList = new ArrayList<>();
		for(String measure: chartRequest.getMeasures()) {
			CustomerMeasureRequest customerMeasureRequest = new CustomerMeasureRequest();
			customerMeasureRequest.setMeasureName(measure);
			dataRequestList.add(customerMeasureRequest);
		}
		return dataRequestList;
	}

}
