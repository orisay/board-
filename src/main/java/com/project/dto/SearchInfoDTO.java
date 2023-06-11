package com.project.dto;

public class SearchInfoDTO {

	private String nm;
	private String id;
	private String email;
	private String domain;

	public String getNm() {
		return nm;
	}
	public String getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getDomain() {
		return domain;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchInfoDTO [nm=");
		builder.append(nm);
		builder.append(", id=");
		builder.append(id);
		builder.append(", email=");
		builder.append(email);
		builder.append(", domain=");
		builder.append(domain);
		builder.append("]");
		return builder.toString();
	}




}
