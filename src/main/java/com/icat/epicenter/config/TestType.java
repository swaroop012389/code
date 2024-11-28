package com.icat.epicenter.config;

public enum TestType {
	COMMERCIAL("commercial"),
	HIHO("hiho"),
	NAHO("naho");

	private String name;

	private TestType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
