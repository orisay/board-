package com.project.dto;

import java.util.Date;

public class MainDTO {

	private String catDomain;
	private String ttl;
	private Date ctrTm;

	public String getCatDomain() {
		return catDomain;
	}
	public String getTtl() {
		return ttl;
	}
	public Date getCtrTm() {
		return ctrTm;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public void setCtrTm(Date ctrTm) {
		this.ctrTm = ctrTm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("mainDTO [catDomain=");
		builder.append(catDomain);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", ctrTm=");
		builder.append(ctrTm);
		builder.append("]");
		return builder.toString();
	}


}
