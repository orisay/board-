package com.project.dto;

public class CheckRightCatDTO {

	private Integer roleNum;
	private String catDomain;
	private String Id;

	public Integer getRoleNum() {
		return roleNum;
	}
	public String getCatDomain() {
		return catDomain;
	}
	public String getId() {
		return Id;
	}
	public void setRoleNum(Integer roleNum) {
		this.roleNum = roleNum;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setId(String id) {
		Id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CheckRightCatDTO [roleNum=");
		builder.append(roleNum);
		builder.append(", catDomain=");
		builder.append(catDomain);
		builder.append(", Id=");
		builder.append(Id);
		builder.append("]");
		return builder.toString();
	}


}
