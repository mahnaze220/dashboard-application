package com.sample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.service.IMetricService;

/**
 * This aspect used to get number of service calls of customer service to perform queries  
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Component
@Aspect
public class MetricAspect {

	private static final String QUERY = "query";

	@Autowired
	private IMetricService metricService;

	public void updateStatistics() {
		metricService.increaseCount(QUERY);		
	}

	@After("@annotation(Metric)")
	public void saveStatistic(JoinPoint joinPoint) {
		updateStatistics();
	}
}
