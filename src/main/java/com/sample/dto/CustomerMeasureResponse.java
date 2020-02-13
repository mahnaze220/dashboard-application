package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains response data for fetched measures. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CustomerMeasureResponse {

	@NonNull
	@JsonProperty("name")
	@ApiModelProperty(notes = "name")
	private String name;
	
	@NonNull
	@JsonProperty("data")
	@ApiModelProperty(notes = "data")
	private List<Double> data;
}
