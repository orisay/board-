package com.project.dto.mb;

import java.util.Date;

public class MbDTO {

	private String id;
    private String als;
    private String pw;
    private String nm;
    private String email;
    private String domain;
    private String addr1;
    private String addr2;
    private String phon1;
    private String phon2;
    private String phon3;
    private Date crtTm;
    private String tel1;
    private String tel2;
    private String tel3;

    public String getId() {
		return id;
	}
	public String getAls() {
		return als;
	}
	public String getPw() {
		return pw;
	}
	public String getNm() {
		return nm;
	}
	public String getEmail() {
		return email;
	}
	public String getDomain() {
		return domain;
	}
	public String getAddr1() {
		return addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public String getPhon1() {
		return phon1;
	}
	public String getPhon2() {
		return phon2;
	}
	public String getPhon3() {
		return phon3;
	}
	public Date getCrtTm() {
		return crtTm;
	}
	public String getTel1() {
		return tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAls(String als) {
		this.als = als;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public void setPhon1(String phon1) {
		this.phon1 = phon1;
	}
	public void setPhon2(String phon2) {
		this.phon2 = phon2;
	}
	public void setPhon3(String phon3) {
		this.phon3 = phon3;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MbDTO [id=");
		builder.append(id);
		builder.append(", als=");
		builder.append(als);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", nm=");
		builder.append(nm);
		builder.append(", email=");
		builder.append(email);
		builder.append(", domain=");
		builder.append(domain);
		builder.append(", addr1=");
		builder.append(addr1);
		builder.append(", addr2=");
		builder.append(addr2);
		builder.append(", phon1=");
		builder.append(phon1);
		builder.append(", phon2=");
		builder.append(phon2);
		builder.append(", phon3=");
		builder.append(phon3);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", tel1=");
		builder.append(tel1);
		builder.append(", tel2=");
		builder.append(tel2);
		builder.append(", tel3=");
		builder.append(tel3);
		builder.append("]");
		return builder.toString();
	}

}
