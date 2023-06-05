package com.project.dto;

import java.util.Date;

public class ReplyDTO {

    private Integer rplNum;
    private Integer boardNum;
    private String id;
    private String rplCn;
    private Date crtTm;
    private Date upTm;
    private Integer depth;
    private String pw;
    private Integer parentRpl;

    public Integer getRplNum() {
		return rplNum;
	}
	public Integer getBoardNum() {
		return boardNum;
	}
	public String getId() {
		return id;
	}
	public String getRplCn() {
		return rplCn;
	}
	public Date getCrtTm() {
		return crtTm;
	}
	public Date getUpTm() {
		return upTm;
	}
	public Integer getDepth() {
		return depth;
	}
	public String getPw() {
		return pw;
	}
	public Integer getParentRpl() {
		return parentRpl;
	}
	public void setRplNum(Integer rplNum) {
		this.rplNum = rplNum;
	}
	public void setBoardNum(Integer boardNum) {
		this.boardNum = boardNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRplCn(String rplCn) {
		this.rplCn = rplCn;
	}
	public void setCrtTm(Date crtTm) {
		this.crtTm = crtTm;
	}
	public void setUpTm(Date upTm) {
		this.upTm = upTm;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setParentRpl(Integer parentRpl) {
		this.parentRpl = parentRpl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReplyDTO [rplNum=");
		builder.append(rplNum);
		builder.append(", boardNum=");
		builder.append(boardNum);
		builder.append(", id=");
		builder.append(id);
		builder.append(", rplCn=");
		builder.append(rplCn);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", upTm=");
		builder.append(upTm);
		builder.append(", depth=");
		builder.append(depth);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", parentRpl=");
		builder.append(parentRpl);
		builder.append("]");
		return builder.toString();
	}

}
