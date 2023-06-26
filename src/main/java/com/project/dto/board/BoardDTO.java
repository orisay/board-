package com.project.dto.board;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class BoardDTO {

	@NotNull
    private Integer boardNum;
	@NotBlank
    private String catDomain;
	@NotBlank
    private String creator;
	@NotBlank
    private String ttl;
	@NotBlank
    private String cn;
    private Integer viewCnt;
    private Integer rplCnt;
    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date crtTm;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date upTm;
    private String pw;

    public Integer getBoardNum() {
		return boardNum;
	}
	public String getCatDomain() {
		return catDomain;
	}
	public String getCreator() {
		return creator;
	}
	public String getTtl() {
		return ttl;
	}
	public String getCn() {
		return cn;
	}
	public Integer getViewCnt() {
		return viewCnt;
	}
	public Integer getRplCnt() {
		return rplCnt;
	}
	public Date getCrtTm() {
		return crtTm;
	}
	public Date getUpTm() {
		return upTm;
	}
	public String getPw() {
		return pw;
	}
	public void setBoardNum(Integer boardNum) {
		this.boardNum = boardNum;
	}
	public void setCatDomain(String catDomain) {
		this.catDomain = catDomain;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public void setViewCnt(Integer viewCnt) {
		this.viewCnt = viewCnt;
	}
	public void setRplCnt(Integer rplCnt) {
		this.rplCnt = rplCnt;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	public void setUpTm(Date upTm) {
		this.upTm = upTm;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}

	public BoardDTO() {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardDTO [boardNum=");
		builder.append(boardNum);
		builder.append(", catDomain=");
		builder.append(catDomain);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", cn=");
		builder.append(cn);
		builder.append(", viewCnt=");
		builder.append(viewCnt);
		builder.append(", rplCnt=");
		builder.append(rplCnt);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", upTm=");
		builder.append(upTm);
		builder.append(", pw=");
		builder.append(pw);
		builder.append("]");
		return builder.toString();
	}

}
