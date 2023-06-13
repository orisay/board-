package com.project.dto;

import java.util.List;

public class RequestBoardDTO {
	private List<BoardDTO> list;

	public RequestBoardDTO() {
		super();
	}

	public List<BoardDTO> getList() {
		return list;
	}

	public void setList(List<BoardDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestBoardDTO [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}


}
