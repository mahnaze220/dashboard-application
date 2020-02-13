package com.sample.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sample.exception.DashboardException;
import com.sample.exception.ExceptionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains request data for getting chart data. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChartRequest {

	@NonNull
	@JsonProperty("dimensions")
	@ApiModelProperty(notes = "dimensions")
	private List<String> dimensions;

	@NonNull
	@JsonProperty("measures")
	@ApiModelProperty(notes = "measures")
	private List<String> measures;

	/*
	 * Validate chart request 
	 */
	public void validate() {
		if(getDimensions() == null || getDimensions().isEmpty()) {
			DashboardException.throwException(ExceptionType.INVALID_INPUT_DATA_EXCEPTION, "A Dimension can not be empty");
		}
		if(getMeasures() == null || getMeasures().isEmpty() || getMeasures().size() > 3) {
			DashboardException.throwException(ExceptionType.INVALID_INPUT_DATA_EXCEPTION, 
					"Measures must be contains between 1 and 3 values");
		}
	}
}
