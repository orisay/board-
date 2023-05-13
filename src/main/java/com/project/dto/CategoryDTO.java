package com.project.dto;

import java.util.Date;

public class CategoryDTO {

	private Integer catNum;
    private String cat;
    private Integer boardCnt;
    private Integer rplCnt;
    private String mng;
    private Date crtTm;
    private String crtNm;
    private Date upTm;
    private String upNm;

    public Integer getCatNum() {
		return catNum;
	}
	public String getCat() {
		return cat;
	}
	public Integer getBoardCnt() {
		return boardCnt;
	}
	public Integer getRplCnt() {
		return rplCnt;
	}
	public String getMng() {
		return mng;
	}
	public Date getCrtTm() {
		return crtTm;
	}
	public String getCrtNm() {
		return crtNm;
	}
	public Date getUpTm() {
		return upTm;
	}
	public String getUpNm() {
		return upNm;
	}
	public void setCatNum(Integer catNum) {
		this.catNum = catNum;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public void setBoardCnt(Integer boardCnt) {
		this.boardCnt = boardCnt;
	}
	public void setRplCnt(Integer rplCnt) {
		this.rplCnt = rplCnt;
	}
	public void setMng(String mng) {
		this.mng = mng;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	public void setCrtNm(String crtNm) {
		this.crtNm = crtNm;
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
		builder.append("CategoryDTO [catNum=");
		builder.append(catNum);
		builder.append(", cat=");
		builder.append(cat);
		builder.append(", boardCnt=");
		builder.append(boardCnt);
		builder.append(", rplCnt=");
		builder.append(rplCnt);
		builder.append(", mng=");
		builder.append(mng);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", crtNm=");
		builder.append(crtNm);
		builder.append(", upTm=");
		builder.append(upTm);
		builder.append(", upNm=");
		builder.append(upNm);
		builder.append("]");
		return builder.toString();
	}

}
