package com.project.dto;

public class CheckRightCatDTO {

	private String catDomain;
	private String Id;

	public String getCatDomain() {
		return catDomain;
	}
	public String getId() {
		return Id;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setId(String id) {
		Id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRightCatDTO [catDomain=");
		builder.append(catDomain);
		builder.append(", Id=");
		builder.append(Id);
		builder.append("]");
		return builder.toString();
	}


}
