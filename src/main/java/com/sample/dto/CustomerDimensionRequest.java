package com.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains request data for retrieving dimensions. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class CustomerDimensionRequest {

	@JsonProperty("dimensionName")
	@ApiModelProperty(notes = "dimensionName")
	private String dimensionName;

	public CustomerDimensionRequest() {
		super();
	}

	public CustomerDimensionRequest(String dimensionName) {
		super();
		this.dimensionName = dimensionName;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}
}
