package com.project.dto.mb;

import java.util.List;

public class MbSessionDTO {

	private String id;
	private String pw;
	private String role;
	private List<MbRoleDTO> roleList;
    private String als;
    private String nm;

    public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getRole() {
		return role;
	}
	public List<MbRoleDTO> getRoleList() {
		return roleList;
	}
	public String getAls() {
		return als;
	}
	public String getNm() {
		return nm;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setRoleList(List<MbRoleDTO> roleList) {
		this.roleList = roleList;
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
		builder.append("MbSessionDTO [id=");
		builder.append(id);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", role=");
		builder.append(role);
		builder.append(", roleList=");
		builder.append(roleList);
		builder.append(", als=");
		builder.append(als);
		builder.append(", nm=");
		builder.append(nm);
		builder.append("]");
		return builder.toString();
	}

}
