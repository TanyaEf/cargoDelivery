package com.voroniuk.delivery.constant;


public enum XML {
	// elements names
	COUNTRY("country"),
	REGION("region"),
	CITY("city"),

	//attribute name
	NAME("name"),
	LAT("lat"),
	LON("lon");

	private String value;

	XML(String value) {
		this.value = value;
	}


	public boolean equalsTo(String name) {
		return value.equals(name);
	}

	public String value() {
		return value;
	}
}