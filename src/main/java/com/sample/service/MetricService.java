package com.sample.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sample.dto.Measure;
import com.sample.dto.ReportRequest;
import com.sample.dto.ReportResponse;
import com.sample.dto.TimeUnit;
import com.sample.exception.DashboardException;
import com.sample.exception.ExceptionType;
import com.sample.util.SimpleMovingAverage;

import reactor.core.publisher.Mono;

/**
 * This service provides statistic services. it uses a concurrent hash map to store 
 * statistics of number of calling services and queries.  
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Service
public class MetricService implements IMetricService {

	private static final Logger log = LoggerFactory.getLogger(MetricService.class);
	private ConcurrentMap<String, ConcurrentHashMap<String, Double>> timeMap;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String CHART_SERVICE_URI = "/chart";
	private static final String QUERY = "query";

	public MetricService() {
		super();
		timeMap = new ConcurrentHashMap<>();
	}

	/**
	 * Add new statistic based on request name (chart or query)
	 */
	@Override
	public void increaseCount(final String request) {
		updateTimeMap(request);
	}

	/**
	 * Update existing statistics
	 */
	private void updateTimeMap(final String request) {
		final String time = dateFormat.format(new Date());
		ConcurrentHashMap<String, Double> statusMap = timeMap.get(time);
		if (statusMap == null) {
			statusMap = new ConcurrentHashMap<>();
		}

		Double count = statusMap.get(request);
		if (count == null) {
			count = 1d;
		} else {
			count++;
		}
		statusMap.put(request, count);
		timeMap.put(time, statusMap);
	}

	/**
	 * Retrieves statistics based on last time unit 
	 */
	public Mono<ReportResponse> findStatistics(ReportRequest reportRequest) {
		if(reportRequest == null) {
			throw DashboardException.throwException(ExceptionType.INVALID_INPUT_DATA_EXCEPTION, 
					"Request can not be empty");
		}
		
		//validate report request
		reportRequest.validate();
		
		//filter statistics based on input time
		final int second = reportRequest.getTimeUnit().equals(TimeUnit.MINUTES.getValue()) 
				? reportRequest.getLast() * 60 : reportRequest.getLast();
		Instant instant = Instant.now();
		final Date when = Date.from(instant.minusSeconds(reportRequest.getLast()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(when);
		List<String> result = getTimeMap().keySet().stream()
				.filter(trn -> within(trn, when, second))
				.collect(Collectors.toList());

		//create report response
		ReportResponse reportResponse = new ReportResponse();
		List<String> categories = new ArrayList<>();
		List<Double> requestCounts = new ArrayList<>();
		List<Double> queryCounts = new ArrayList<>();
		List<Double> mavgCounts;
		List<Measure> series = new ArrayList<>();
		Long totalQueries = 0L; 
		Long totalRequests = 0L;
		reportResponse.setCategories(categories);
		reportResponse.setStatistics(series);

		for(String time: result) {
			ConcurrentHashMap<String, Double> requests = getTimeMap().get(time);
			if(!requests.containsKey(CHART_SERVICE_URI) && !requests.containsKey(QUERY)) {
				continue;
			}
			categories.add(time);
			if(requests.containsKey(CHART_SERVICE_URI)) {
				Double value = requests.get(CHART_SERVICE_URI);
				requestCounts.add(value);
				totalRequests = (long) (totalRequests + value);
			}
			if(requests.containsKey(QUERY)) {
				Double value = requests.get(QUERY);
				queryCounts.add(value);
				totalQueries = (long) (totalQueries + value);
			}
		}
		Measure request = new Measure("requests", requestCounts);
		series.add(request);
		
		Measure query = new Measure("queries", queryCounts);
		series.add(query);
		
		if(!request.getData().isEmpty()) {
			mavgCounts = SimpleMovingAverage.compute(request.getData(), reportRequest.getMovingPoints());
			Measure mavg = new Measure("mavg", mavgCounts);
			series.add(mavg);
		}
		
		reportResponse.setTotalQueries(totalQueries);
		reportResponse.setTotalRequests(totalRequests);
		return Mono.just(reportResponse);		
	}
	
	public ConcurrentMap<String, ConcurrentHashMap<String, Double>> getTimeMap() {
		return timeMap;
	}
	
	/**
	 * Check difference between current time and input time  
	 * @param d1
	 * @param d2
	 * @param seconds
	 * @return
	 */
	private boolean within(String d1, Date d2, int seconds) {
		if (d2 == null) {
			return true;
		}

		long diff;
		try {
			diff = d2.toInstant().getEpochSecond() - dateFormat.parse(d1).toInstant().getEpochSecond();
			return diff >= 0 && diff < seconds;
		} catch (ParseException e) {
			log.error("An exception happend in within method", e.getMessage());
		}
		return false;
	}
}