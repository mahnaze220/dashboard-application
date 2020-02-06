package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains request data for retrieving measures. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class CustomerMeasureRequest {

	@JsonProperty("measureName")
	@ApiModelProperty(notes = "measureName")
	private String measureName;

	@JsonProperty("teams")
	@ApiModelProperty(notes = "teams")
	private List<String> teams;

	public CustomerMeasureRequest() {
		super();
	}

	public CustomerMeasureRequest(String measureName, List<String> teams) {
		super();
		this.measureName = measureName;
		this.teams = teams;
	}

	public String getMeasureName() {
		return measureName;
	}

	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}

	public List<String> getTeams() {
		return teams;
	}

	public void setTeams(List<String> teams) {
		this.teams = teams;
	}	
}
