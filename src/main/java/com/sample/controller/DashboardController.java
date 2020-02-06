package com.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.dto.ChartRequest;
import com.sample.dto.ChartResponse;
import com.sample.dto.ReportRequest;
import com.sample.dto.ReportResponse;
import com.sample.service.DashboardService;
import com.sample.service.IMetricService;

import reactor.core.publisher.Mono;

/**
 * This rest service provides some services for getting chart and metrics on customer data.
 * For reactive, asynchronous and concurrent request and also thread safety, this controller uses 
 * WebFlux framework and it's scope is request. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@RestController
@Scope("request")
public class DashboardController {

	@Autowired
	private IMetricService metricService;

	@Autowired
	private DashboardService dashboardService;

	/**
	 * This service get chart data based on customer data queries.
	 * This service is allowed for both USER and ADMIN roles.
	 * @param chartRequest
	 * @return ChartResponse
	 */
	@PostMapping("/chart")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Mono<ChartResponse> displayChart(@RequestBody ChartRequest chartRequest) {		
		return dashboardService.getChartData(chartRequest);
	}

	/**
	 * This service retrieves statistics about dashboard application.
	 * This service is allowed just for ADMIN role.
	 * @param reportRequest
	 * @return ReportResponse
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/metric")
	public Mono<ReportResponse> getMetric(@RequestBody ReportRequest reportRequest) {
		return metricService.findStatistics(reportRequest);
	}
}
