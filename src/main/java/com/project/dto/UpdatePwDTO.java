package com.project.dto;

public class UpdatePwDTO {

	private String id;
	private String pw;
	private String newPw;

	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getNewPw() {
		return newPw;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdatePwDTO [id=");
		builder.append(id);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", newPw=");
		builder.append(newPw);
		builder.append("]");
		return builder.toString();
	}





}
