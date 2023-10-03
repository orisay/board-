package com.project.dto.reply;

import java.util.List;

public class RequestReplyDTO {
	private List<ReplyDTO> list;

	public RequestReplyDTO() {
	}

	public List<ReplyDTO> getList() {
		return list;
	}

	public void setList(List<ReplyDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestReplyDTO [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}





}
