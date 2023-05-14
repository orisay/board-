package com.project.dto;

public class BoardSearchDTO {

	private String cat;
	private Integer startIdx;
	private Integer endIdx;
	private String target;
	private String keyword;
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
	public String getKeyword() {
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
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setAll(String all) {
		this.all = all;
	}


}
