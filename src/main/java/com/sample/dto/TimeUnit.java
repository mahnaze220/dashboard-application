package com.sample.dto;

public enum TimeUnit {
	MINUTES("minutes"),
	SECONDS("seconds");

	private String value;

	TimeUnit(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static TimeUnit getFromValue(String value) {
		for(TimeUnit unit: values()) {
			if(unit.getValue().equals(value) ){
				return unit;
			}
		}
		return null;
	}
}
