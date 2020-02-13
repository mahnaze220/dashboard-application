package com.sample.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains response data for a chart request. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartResponse {

	@NotNull
	@JsonProperty("categories")
	@ApiModelProperty(notes = "categories")
	private List<String> categories;
	
	@NotNull
	@JsonProperty("series")
	@ApiModelProperty(notes = "series")
	private List<Measure> series;
}
