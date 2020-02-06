package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains response data of fetched dimensions. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class CustomerDimensionResponse {

	@JsonProperty("team")
	@ApiModelProperty(notes = "team")
	private List<String> data;

	public CustomerDimensionResponse() {
		super();
	}

	public CustomerDimensionResponse(List<String> data) {
		super();
		this.data = data;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
	
}
