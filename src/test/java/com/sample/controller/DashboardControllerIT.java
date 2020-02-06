package com.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.dto.ChartRequest;
import com.sample.dto.ChartResponse;
import com.sample.dto.Measure;
import com.sample.dto.ReportRequest;
import com.sample.dto.ReportResponse;
import com.sample.dto.TimeUnit;
import com.sample.service.DashboardService;
import com.sample.service.MetricService;

import reactor.core.publisher.Mono;

/**
 * This integration test contains test scenarios for DashboardController. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerIT {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String CHART_SERVICE_NAME = "/chart";
	private static final String METRIC_SERVICE_NAME = "/metric";
	private static final String REVENUES = "revenues";
	private static final String CHAMPIONS = "champions";
	private static final String LEAGUES = "leagues";
	private static final String REQUESTS = "requests";
	private static final String QUERIES = "queries";
	private static final String MVAG = "mavg";

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private DashboardService dashboardService;

	@Mock
	private MetricService metricService;

	@Test
	@WithMockUser(username = "user")
	public void displayChart_whenSetTeamDimensionWithUserRole_thenGetMeasures() throws IOException, Exception {

		ChartRequest request = createDataRequest();

		Mockito.doReturn(createChartResponse()).when(dashboardService).getChartData(request);

		MvcResult asyncResult = mockMvc
				.perform(post(CHART_SERVICE_NAME)
						.content(convertObjectToJson(request))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON))

				.andReturn();
		final MvcResult result = mockMvc.perform(asyncDispatch(asyncResult))
				.andExpect(status().isOk())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		ChartResponse chartResponse = mapper.readValue(result.getResponse().getContentAsString(), ChartResponse.class);
		Assertions.assertEquals(3, chartResponse.getSeries().size());
	}

	@Test
	@WithMockUser(username = "user")
	public void getMetric_whenSetLastFiveMinutesWithUserRole_thenGetForbiddenResponseCode() throws IOException, Exception {

		ReportRequest request = createReportRequest();
		Mockito.doReturn(createMockReportResponse()).when(metricService).findStatistics(Mockito.any());

		mockMvc
		.perform(post(METRIC_SERVICE_NAME)
				.content(convertObjectToJson(request))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isForbidden());
	}

	public String convertObjectToJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	private ChartRequest createDataRequest() {
		List<String> dimensions = new ArrayList<>();
		dimensions.add("team");
		List<String> measures = new ArrayList<>();
		measures.add("leagues");
		measures.add("champions");
		measures.add("revenues");
		return new ChartRequest(dimensions, measures);
	}

	private ReportRequest createReportRequest() {
		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setLast(5);
		reportRequest.setMovingPoints(5);
		reportRequest.setTimeUnit(TimeUnit.MINUTES.getValue());
		return reportRequest;
	}

	private Mono<ChartResponse> createChartResponse() {
		ChartResponse chartResponse = new ChartResponse();
		chartResponse.setCategories(createCategories());
		List<Measure> series = new ArrayList<>();
		series.add(new Measure(LEAGUES, createAllLeagues()));
		series.add(new Measure(CHAMPIONS, createAllChampions()));
		series.add(new Measure(REVENUES, createAllRevenues()));
		chartResponse.setSeries(series);
		return Mono.just(chartResponse);
	}

	public List<String> createCategories() {
		List<String> data = new ArrayList<>();
		data.add("Real Madrid");
		data.add("Barcelona");
		data.add("Bayern Munich");
		data.add("Liverpool");
		data.add("Milan");
		return data;
	}

	private Mono<ReportResponse> createMockReportResponse() {
		ReportResponse reportResponse = new ReportResponse();
		reportResponse.setTotalQueries(30l);
		reportResponse.setTotalRequests(10l);
		List<String> categories = new ArrayList<>();
		ZonedDateTime now = ZonedDateTime.now();
		categories.add(now.minusMinutes(1).format(dateFormatter));
		categories.add(now.minusMinutes(2).format(dateFormatter));
		categories.add(now.minusMinutes(3).format(dateFormatter));
		categories.add(now.minusMinutes(4).format(dateFormatter));		
		reportResponse.setCategories(categories);

		List<Measure> measures = new ArrayList<Measure>();
		List<Double> requests = new ArrayList<>();
		requests.add(50d);
		requests.add(80d);
		requests.add(20d);
		requests.add(50d);
		requests.add(50d);
		List<Double> queries = new ArrayList<>();
		queries.add(100d);
		queries.add(150d);
		queries.add(50d);
		queries.add(75d);
		queries.add(75d);
		List<Double> mavg = new ArrayList<>();
		mavg.add(122.5d);
		mavg.add(150d);
		mavg.add(75d);
		mavg.add(62.7d);
		mavg.add(83.5d);
		measures.add(new Measure(REQUESTS, requests));
		measures.add(new Measure(QUERIES, queries));
		measures.add(new Measure(MVAG, mavg));
		reportResponse.setStatistics(measures);
		return Mono.just(reportResponse);
	}

	public List<Double> createAllRevenues() {
		List<Double> revenues = new ArrayList<>();
		revenues.add(625d);
		revenues.add(620d);
		revenues.add(600d);
		revenues.add(400d);
		revenues.add(250d);
		return revenues;
	}

	public List<Double> createAllLeagues() {
		List<Double> leagues = new ArrayList<>();
		leagues.add(33d);
		leagues.add(24d);
		leagues.add(26d);
		leagues.add(18d);
		leagues.add(18d);
		return leagues;
	}

	public List<Double> createAllChampions() {
		List<Double> champions = new ArrayList<>();
		champions.add(12d);
		champions.add(5d);
		champions.add(5d);
		champions.add(5d);
		champions.add(7d);
		return champions;
	}
}
