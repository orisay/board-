package com.project.dto.board;

public class PlusPointBoardDTO {

	private Integer boardNum;
	private String id;
	private Integer point;
	public Integer getBoardNum() {
		return boardNum;
	}
	public String getId() {
		return id;
	}
	public Integer getPoint() {
		return point;
	}
	public void setBoardNum(Integer boardNum) {
		this.boardNum = boardNum;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlusPointBoardDTO [boardNum=");
		builder.append(boardNum);
		builder.append(", id=");
		builder.append(id);
		builder.append(", point=");
		builder.append(point);
		builder.append("]");
		return builder.toString();
	}


}
