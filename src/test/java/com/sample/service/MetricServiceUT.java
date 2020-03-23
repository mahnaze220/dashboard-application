package com.sample.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sample.dto.ReportRequest;
import com.sample.dto.ReportResponse;
import com.sample.dto.TimeUnit;
import com.sample.exception.DashboardException.InvalidInputDataException;

import reactor.core.publisher.Mono;

/**
 * This unit test contains test scenarios for MetricService. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@SpringBootTest
public class MetricServiceUT {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static final String CHART_SERVICE_URI = "/chart";
	private static final String QUERY = "query";	

	@Autowired
	private MetricService metricService;

	@Test
	public void findStatistics_whenSetFiveLastMinutes_thenReturnStatistics() {

		MetricService metricServiceSpy = Mockito.spy(metricService);

		Mockito.doReturn(createMockMinutesUnitTimeMap()).when(metricServiceSpy).getTimeMap();

		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setLast(5);
		reportRequest.setTimeUnit(TimeUnit.MINUTES.getValue());
		reportRequest.setMovingPoints(5);
		Mono<ReportResponse> resposne = metricServiceSpy.findStatistics(reportRequest);
		ReportResponse result = resposne.block();
		Assertions.assertEquals(16, result.getTotalRequests().longValue());
		Assertions.assertEquals(20, result.getTotalQueries().longValue());
		Assertions.assertEquals(2, result.getCategories().size());
	}

	@Test
	public void findStatistics_whenSetFiveLastSeconds_thenReturnStatistics() {

		MetricService metricServiceSpy = Mockito.spy(metricService);

		Mockito.doReturn(createMockSecondsUnitTimeMap()).when(metricServiceSpy).getTimeMap();

		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setLast(2);
		reportRequest.setTimeUnit(TimeUnit.SECONDS.getValue());
		Mono<ReportResponse> resposne = metricServiceSpy.findStatistics(reportRequest);
		ReportResponse result = resposne.block();
		Assertions.assertEquals(0, result.getTotalRequests().longValue());
		Assertions.assertEquals(0, result.getTotalQueries().longValue());
	}

	@Test
	public void findStatistics_whenSetInvalidTimeUnit_thenThrowInvalidInputDataException() {

		MetricService metricServiceSpy = Mockito.spy(metricService);

		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setLast(5);
		reportRequest.setTimeUnit("hours");
		Assertions.assertThrows(InvalidInputDataException.class, () -> {
			metricServiceSpy.findStatistics(reportRequest);
		});
	}

	@Test
	public void findStatistics_whenSetNullTimeUnit_thenThrowInvalidInputDataException() {

		MetricService metricServiceSpy = Mockito.spy(metricService);
		Assertions.assertThrows(InvalidInputDataException.class, () -> {
			metricServiceSpy.findStatistics(null);
		});
	}

	private ConcurrentMap<String, ConcurrentHashMap<String, Double>> createMockMinutesUnitTimeMap(){
		ConcurrentMap<String, ConcurrentHashMap<String, Double>> timeMap = new ConcurrentHashMap<>();
		ConcurrentHashMap<String, Double> statusMap = new ConcurrentHashMap<>();

		ZonedDateTime now = ZonedDateTime.now();
		final String time1 = now.minusMinutes(6).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 3d);
		timeMap.put(time1, statusMap);

		final String time2 = now.minusMinutes(7).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 1d);
		timeMap.put(time2, statusMap);

		final String time3 = now.minusMinutes(1).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 5d);
		timeMap.put(time3, statusMap);

		final String time5 = now.minusMinutes(2).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 8d);
		timeMap.put(time5, statusMap);

		final String time4 = now.minusMinutes(1).format(dateFormatter);
		statusMap.put(QUERY, 10d);
		timeMap.put(time4, statusMap);

		return timeMap;
	}

	private ConcurrentMap<String, ConcurrentHashMap<String, Double>> createMockSecondsUnitTimeMap(){
		ConcurrentMap<String, ConcurrentHashMap<String, Double>> timeMap = new ConcurrentHashMap<>();
		ConcurrentHashMap<String, Double> statusMap = new ConcurrentHashMap<>();

		ZonedDateTime now = ZonedDateTime.now();
		final String time1 = now.minusDays(1).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 3d);
		timeMap.put(time1, statusMap);

		final String time2 = now.minusSeconds(100).format(dateFormatter);
		statusMap.put(QUERY, 1d);
		timeMap.put(time2, statusMap);

		final String time3 = now.minusSeconds(10).format(dateFormatter);
		statusMap.put(CHART_SERVICE_URI, 5d);
		timeMap.put(time3, statusMap);

		return timeMap;
	}
}
