package com.project.dto;

public class MyResponseEntityDTO<T> {

	private String mesg;
	private T body;

	public MyResponseEntityDTO() {
		super();
	}

	public MyResponseEntityDTO(String mesg) {
		super();
		this.mesg = mesg;
	}

	public MyResponseEntityDTO(String mesg, T body) {
		super();
		this.mesg = mesg;
		this.body = body;
	}

	public String getMesg() {
		return mesg;
	}

	public T getBody() {
		return body;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
