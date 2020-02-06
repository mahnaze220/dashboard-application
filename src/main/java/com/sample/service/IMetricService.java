package com.sample.service;

import com.sample.dto.ReportRequest;
import com.sample.dto.ReportResponse;

import reactor.core.publisher.Mono;

/**
 * @author Mahnaz
 * @Jan 31, 2020
 */

public interface IMetricService {

	void increaseCount(final String request);
	Mono<ReportResponse> findStatistics(ReportRequest reportRequest);
}

