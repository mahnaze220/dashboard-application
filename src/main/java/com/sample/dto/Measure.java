package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains measure data. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class Measure {

	@JsonProperty("name")
	@ApiModelProperty(notes = "name")
	private String name;
	
	@JsonProperty("data")
	@ApiModelProperty(notes = "data")
	private List<Double> data;

	public Measure() {
		super();
	}

	public Measure(String name, List<Double> data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
}

