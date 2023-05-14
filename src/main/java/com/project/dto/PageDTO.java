package com.project.dto;

import java.util.List;

public class PageDTO {

	private Integer perPage;
	private Integer curPage;
	private Integer totalPage;
	private List<?> list;

	public Integer getPerPage() {
		return perPage;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public List<?> getList() {
		return list;
	}
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageDTO [perPage=");
		builder.append(perPage);
		builder.append(", curPage=");
		builder.append(curPage);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}


}
