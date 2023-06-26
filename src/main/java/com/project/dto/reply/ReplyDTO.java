package com.project.dto.reply;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class ReplyDTO {

	@NotBlank
    private Integer rplNum;
	@NotBlank
    private Integer boardNum;
	@NotBlank
    private String creator;
    private String rplCn;
    private Integer depth;
	@NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date crtTm;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date upTm;
    private String pw;
    private Integer parentRpl;
	public Integer getRplNum() {
		return rplNum;
	}
	public Integer getBoardNum() {
		return boardNum;
	}
	public String getCreator() {
		return creator;
	}
	public String getRplCn() {
		return rplCn;
	}
	public Integer getDepth() {
		return depth;
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
	public Integer getParentRpl() {
		return parentRpl;
	}
	public void setRplNum(Integer rplNum) {
		this.rplNum = rplNum;
	}
	public void setBoardNum(Integer boardNum) {
		this.boardNum = boardNum;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setRplCn(String rplCn) {
		this.rplCn = rplCn;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
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
		builder.append(", creator=");
		builder.append(creator);
		builder.append(", rplCn=");
		builder.append(rplCn);
		builder.append(", depth=");
		builder.append(depth);
		builder.append(", crtTm=");
		builder.append(crtTm);
		builder.append(", upTm=");
		builder.append(upTm);
		builder.append(", pw=");
		builder.append(pw);
		builder.append(", parentRpl=");
		builder.append(parentRpl);
		builder.append("]");
		return builder.toString();
	}

}
