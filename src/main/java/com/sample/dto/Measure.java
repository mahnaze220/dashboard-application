package com.sample.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains measure data. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measure {

	@NotNull
	@JsonProperty("name")
	@ApiModelProperty(notes = "name")
	private String name;
	
	@NotNull
	@JsonProperty("data")
	@ApiModelProperty(notes = "data")
	private List<Double> data;
}

