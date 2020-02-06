package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains response data for report statistics request. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class ReportResponse {

	@JsonProperty("totalRequests")
	@ApiModelProperty(notes = "totalRequests")
	private Long totalRequests;

	@JsonProperty("totalQueries")
	@ApiModelProperty(notes = "totalQueries")
	private Long totalQueries;

	@JsonProperty("series")
	@ApiModelProperty(notes = "series")
	private List<Measure> statistics;

	@JsonProperty("categories")
	@ApiModelProperty(notes = "categories")
	private List<String> categories;

	public ReportResponse() {
		super();
	}

	public Long getTotalRequests() {
		return totalRequests;
	}

	public void setTotalRequests(Long totalRequests) {
		this.totalRequests = totalRequests;
	}

	public Long getTotalQueries() {
		return totalQueries;
	}

	public void setTotalQueries(Long totalQueries) {
		this.totalQueries = totalQueries;
	}

	public List<Measure> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Measure> statistics) {
		this.statistics = statistics;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
}
