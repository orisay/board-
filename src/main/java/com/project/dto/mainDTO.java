package com.project.dto;

import java.util.Date;

public class mainDTO {

	private String cat;
	private String ttl;
	private Date ctrTm;

	public String getCat() {
		return cat;
	}
	public String getTtl() {
		return ttl;
	}
	public Date getCtrTm() {
		return ctrTm;
	}
	public void setCat(String cat) {
		this.cat = cat;
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
		builder.append("mainDTO [cat=");
		builder.append(cat);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", ctrTm=");
		builder.append(ctrTm);
		builder.append("]");
		return builder.toString();
	}

}
