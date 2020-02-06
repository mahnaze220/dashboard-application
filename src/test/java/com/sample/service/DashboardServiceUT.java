package com.sample.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.sample.config.ConfigProperties;
import com.sample.dto.CustomerDimensionRequest;
import com.sample.dto.CustomerMeasureRequest;
import com.sample.dto.CustomerMeasureResponse;
import com.sample.exception.DashboardException.InvalidInputDataException;

/**
 * This unit test contains test scenarios for DashBoardService. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@SpringBootTest
public class DashboardServiceUT {

	private static final String TEAMS = "team";
	private static final String REVENUES = "revenues";
	private static final String CHAMPIONS = "champions";
	private static final String LEAGUES = "leagues";

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private ConfigProperties configProperties;

	private MockRestServiceServer mockServer;

	@BeforeEach
	public void setup() {
		RestTemplate restTemplate = new RestTemplate();
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void callCustomerDataService_whenSetTeamDimension_thenReturnTeamList() {

		mockServer.expect(requestTo(configProperties.getDataServiceURI()))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

		List<String> result = dashboardService.getDimensionsFromCustomerService(new CustomerDimensionRequest(TEAMS));
		Assertions.assertArrayEquals(createMockCategories().toArray(), result.toArray());
	}

	@Test
	public void callCustomerMeasureService_whenSetThreeDimension_thenReturnAllTeamMeasures() {

		mockServer.expect(requestTo(configProperties.getMeasureServiceURI()))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

		List<CustomerMeasureRequest> data = new ArrayList<>();
		data.add(new CustomerMeasureRequest(LEAGUES, null));
		data.add(new CustomerMeasureRequest(CHAMPIONS, null));
		data.add(new CustomerMeasureRequest(REVENUES, null));

		List<CustomerMeasureResponse> result = dashboardService.getMeasureFromCustomerService(data);
		Assertions.assertEquals(3, result.size());
		Assertions.assertTrue(result.get(0).getName().contains(LEAGUES));
	}

	@Test
	public void callCustomerMeasureService_whenSetTwoDimension_thenReturnTwoTeamMeasures() {

		mockServer.expect(requestTo(configProperties.getMeasureServiceURI()))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

		List<CustomerMeasureRequest> data = new ArrayList<>();
		data.add(new CustomerMeasureRequest(LEAGUES, null));
		data.add(new CustomerMeasureRequest(CHAMPIONS, null));

		List<CustomerMeasureResponse> result = dashboardService.getMeasureFromCustomerService(data);
		Assertions.assertEquals(2, result.size());
		Assertions.assertTrue(result.get(0).getName().contains(LEAGUES));
	}

	@Test
	public void callCustomerMeasureService_whenSetOneDimension_thenReturnOneTeamMeasures() {

		mockServer.expect(requestTo(configProperties.getMeasureServiceURI()))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));

		List<CustomerMeasureRequest> data = new ArrayList<>();
		data.add(new CustomerMeasureRequest(LEAGUES, null));

		List<CustomerMeasureResponse> result = dashboardService.getMeasureFromCustomerService(data);
		Assertions.assertEquals(1, result.size());
		Assertions.assertTrue(result.get(0).getName().contains(LEAGUES));
	}

	@Test
	public void getChartData_whenSetNoDimension_thenThrowInvalidInputDataException() {

		Assertions.assertThrows(InvalidInputDataException.class, () -> {
			dashboardService.getChartData(null);
		});
	}

	private List<String> createMockCategories(){
		List<String> data = new ArrayList<>();
		data.add("Real Madrid");
		data.add("Barcelona");
		data.add("Bayern Munich");
		data.add("Liverpool");
		data.add("Milan");
		return data;
	}
}
