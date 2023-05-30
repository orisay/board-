package com.project.dto;

public class CheckRightCatDTO {

	private String catDomain;
	private String memberId;
	public String getCatDomain() {
		return catDomain;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRightCatDTO [catDomain=");
		builder.append(catDomain);
		builder.append(", memberId=");
		builder.append(memberId);
		builder.append("]");
		return builder.toString();
	}


}
