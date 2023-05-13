package com.project.dto;

import java.util.Date;

public class BoardDTO {

    private Integer boardNum;
    private Integer catNum;
    private String creator;
    private String ttl;
    private String cn;
    private Integer view;
    private Integer rplCnt;
    private Date crtTm;
    private Date upTm;
    private String upNm;

    public Integer getBoardNum() {
		return boardNum;
	}
	public Integer getCatNum() {
		return catNum;
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
	public Integer getView() {
		return view;
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
	public String getUpNm() {
		return upNm;
	}
	public void setBoardNum(Integer boardNum) {
		this.boardNum = boardNum;
	}
	public void setCatNum(Integer catNum) {
		this.catNum = catNum;
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
	public void setView(Integer view) {
		this.view = view;
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
	public void setUpNm(String upNm) {
		this.upNm = upNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardDTO [boardNum=");
		builder.append(boardNum);
		builder.append(", catNum=");
		builder.append(catNum);
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", ttl=");
		builder.append(ttl);
		builder.append(", cn=");
		builder.append(cn);
		builder.append(", view=");
		builder.append(view);
		builder.append(", rplCnt=");
		builder.append(rplCnt);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", upTm=");
		builder.append(upTm);
		builder.append(", upNm=");
		builder.append(upNm);
		builder.append("]");
		return builder.toString();
	}

}
