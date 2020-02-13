package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains request data for retrieving measures. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@NoArgsConstructor
public class CustomerMeasureRequest {

	@JsonProperty("measureName")
	@ApiModelProperty(notes = "measureName")
	private String measureName;

	@JsonProperty("teams")
	@ApiModelProperty(notes = "teams")
	private List<String> teams;

	public CustomerMeasureRequest(String measureName, List<String> teams) {
		super();
		this.measureName = measureName;
		this.teams = teams;
	}
}
