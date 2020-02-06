package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains response data for a chart request. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class ChartResponse {

	@JsonProperty("categories")
	@ApiModelProperty(notes = "categories")
	private List<String> categories;
	
	@JsonProperty("series")
	@ApiModelProperty(notes = "series")
	private List<Measure> series;

	public ChartResponse() {
		super();
	}

	public ChartResponse(List<String> categories, List<Measure> series) {
		super();
		this.categories = categories;
		this.series = series;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Measure> getSeries() {
		return series;
	}

	public void setSeries(List<Measure> series) {
		this.series = series;
	}
}
