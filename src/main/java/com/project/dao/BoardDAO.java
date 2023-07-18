package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.board.BoardDTO;
import com.project.dto.board.BoardDetailDTO;
import com.project.dto.board.BoardSearchDTO;
import com.project.dto.board.PlusPointBoardDTO;
import com.project.dto.mb.InsertUserRoleDTO;
import com.project.dto.reply.ReplyDTO;
import com.project.dto.MainDTO;

@Mapper
public interface BoardDAO {

	List<MainDTO> getFirstWindow();

	Integer totalCountByCat(String catDomain);

	Integer totalCountByBoard(Integer boardNum);

	Integer checkUserBlockStatus(InsertUserRoleDTO insertUserRoleDTO);

	List<BoardDTO> getBoardList(BoardSearchDTO boardSearchDTO);

	Integer createBoard(BoardDTO boardDTO);

	Integer updateBoard(BoardDTO boardDTO);

	Integer deleteBoard(BoardDTO boardDTO);

	List<BoardDTO> searchBoardBasic(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardComplex(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardAll(BoardSearchDTO boardSearchDTO);

	void updateViewCount(BoardDetailDTO boardDetailDTO);

	BoardDetailDTO showBoard(BoardDetailDTO boardDetailDTO);

	List<ReplyDTO> getReplyList(BoardSearchDTO boardSearchDTO);

	void backupBoard(BoardDTO boardDTO);

	void increaseBoardCount(BoardDTO boardDTO);

	void decreaseBoardCount(BoardDTO boardDTO);

	boolean canGetPoint(PlusPointBoardDTO plusPointBoardDTO);

	Integer getGoodPoint(PlusPointBoardDTO plusPointBoardDTO);

	Integer getBadPoint(PlusPointBoardDTO plusPointBoardDTO);

}
