package com.project.dto;

import java.util.List;

public class BoardSearchSpecialDTO {

	private String cat;
	private Integer startIdx;
	private Integer endIdx;
	private String target;
	private List<?> keyword;
	private String all;
	public String getCat() {
		return cat;
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
	public List<?> getKeyword() {
		return keyword;
	}
	public String getAll() {
		return all;
	}
	public void setCat(String cat) {
		this.cat = cat;
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
	public void setKeyword(List<?> keyword) {
		this.keyword = keyword;
	}
	public void setAll(String all) {
		this.all = all;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardSearchSpecialDTO [cat=");
		builder.append(cat);
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
