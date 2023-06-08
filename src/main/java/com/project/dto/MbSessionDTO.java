package com.project.dto;

public class MbSessionDTO {

	private Integer roleNum;
	private String id;
	private String catDomain;
	private String role;
    private String als;
    private String nm;

    public Integer getRoleNum() {
		return roleNum;
	}
	public String getId() {
		return id;
	}
	public String getCatDomain() {
		return catDomain;
	}
	public String getRole() {
		return role;
	}
	public String getAls() {
		return als;
	}
	public String getNm() {
		return nm;
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
	public void setRole(String role) {
		this.role = role;
	}
	public void setAls(String als) {
		this.als = als;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MbSessionDTO [roleNum=");
		builder.append(roleNum);
		builder.append(", id=");
		builder.append(id);
		builder.append(", catDomain=");
		builder.append(catDomain);
		builder.append(", role=");
		builder.append(role);
		builder.append(", als=");
		builder.append(als);
		builder.append(", nm=");
		builder.append(nm);
		builder.append("]");
		return builder.toString();
	}

}
