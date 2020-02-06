package com.sample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sample.exception.DashboardException;
import com.sample.exception.ExceptionType;

import io.swagger.annotations.ApiModelProperty;

/**
 * This DTO contains request data for report statistics. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

public class ReportRequest {

	@JsonProperty("last")
	@ApiModelProperty(notes = "last")
	private Integer last;

	@JsonProperty("timeUnit")
	@ApiModelProperty(notes = "timeUnit")
	private String timeUnit;

	@JsonProperty("mavgPoints")
	@ApiModelProperty(notes = "mavgPoints")
	private Integer movingPoints;

	public ReportRequest() {
		super();
	}

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

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getMovingPoints() {
		return movingPoints;
	}

	public void setMovingPoints(Integer movingPoints) {
		this.movingPoints = movingPoints;
	}
}
