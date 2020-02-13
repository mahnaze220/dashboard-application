package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains response data for report statistics request. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@NoArgsConstructor
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

}
