package com.sample.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sample.exception.DashboardException;
import com.sample.exception.ExceptionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO contains request data for report statistics. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@NoArgsConstructor
public class ReportRequest {

	@NotNull
	@JsonProperty("last")
	@ApiModelProperty(notes = "last")
	private Integer last;

	@NotNull
	@JsonProperty("timeUnit")
	@ApiModelProperty(notes = "timeUnit")
	private String timeUnit;

	@NotNull
	@JsonProperty("mavgPoints")
	@ApiModelProperty(notes = "mavgPoints")
	private Integer movingPoints;

	/*
	 * Validate report request
	 */
	public void validate() {
		if(getTimeUnit() == null || 
				(!TimeUnit.MINUTES.getValue().equals(getTimeUnit()) 
						&& !TimeUnit.SECONDS.getValue().equals(getTimeUnit()))) {
			throw DashboardException.throwException(ExceptionType.INVALID_INPUT_DATA_EXCEPTION, 
					"Time unit value can be minutes or seconds");
		}
	}
}
