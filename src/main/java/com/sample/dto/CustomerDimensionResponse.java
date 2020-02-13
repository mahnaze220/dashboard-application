package com.sample.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * This DTO contains response data of fetched dimensions. 
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Data
@RequiredArgsConstructor
public class CustomerDimensionResponse {

	@NotNull
	@JsonProperty("team")
	@ApiModelProperty(notes = "team")
	private List<String> data;
	
}
