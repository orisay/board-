package com.project.dto.mb;

public class MbRoleDTO {

	private Integer roleNum;
	private String id;
	private String catDomain;

	public Integer getRoleNum() {
		return roleNum;
	}
	public String getId() {
		return id;
	}
	public String getCatDomain() {
		return catDomain;
	}
	public void setRoleNum(Integer roleNum) {
		this.roleNum = roleNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MbRoleDTO [roleNum=");
		builder.append(roleNum);
		builder.append(", id=");
		builder.append(id);
		builder.append(", catDomain=");
		builder.append(catDomain);
		builder.append("]");
		return builder.toString();
	}

}
