package com.project.dto;

public class BoardSearchDTO {

	private String catDomain;
	private Integer startIdx;
	private Integer endIdx;
	private String target;
	private String keyword;
	private String all;

	public String getCatDomain() {
		return catDomain;
	}

	public Integer getStartIdx() {
		return startIdx;
	}

	public Integer getEndIdx() {
		return endIdx;
	}

	public String getTarget() {
		return target;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getAll() {
		return all;
	}

	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}

	public void setStartIdx(Integer startIdx) {
		this.startIdx = startIdx;
	}

	public void setEndIdx(Integer endIdx) {
		this.endIdx = endIdx;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setAll(String all) {
		this.all = all;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardSearchDTO [catDomain=");
		builder.append(catDomain);
		builder.append(", startIdx=");
		builder.append(startIdx);
		builder.append(", endIdx=");
		builder.append(endIdx);
		builder.append(", target=");
		builder.append(target);
		builder.append(", keyword=");
		builder.append(keyword);
		builder.append(", all=");
		builder.append(all);
		builder.append("]");
		return builder.toString();
	}

}
