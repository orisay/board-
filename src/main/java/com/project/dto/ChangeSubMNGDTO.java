package com.project.dto;

public class ChangeSubMNGDTO {

	private String id;
	private String catDomain;
	private Integer roleNum;

	public String getId() {
		return id;
	}
	public String getCatDomain() {
		return catDomain;
	}
	public Integer getRoleNum() {
		return roleNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setRoleNum(Integer roleNum) {
		this.roleNum = roleNum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeSubMNGDTO [id=");
		builder.append(id);
		builder.append(", catDomain=");
		builder.append(catDomain);
		builder.append(", roleNum=");
		builder.append(roleNum);
		builder.append("]");
		return builder.toString();
	}



}
