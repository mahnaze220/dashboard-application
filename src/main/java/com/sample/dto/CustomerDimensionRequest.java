package com.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains request data for retrieving dimensions. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDimensionRequest {

	@JsonProperty("dimensionName")
	@ApiModelProperty(notes = "dimensionName")
	private String dimensionName;

}
