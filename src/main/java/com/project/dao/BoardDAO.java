package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.InsertUserRoleDTO;
import com.project.dto.ReplyDTO;
import com.project.dto.MainDTO;

@Mapper
public interface BoardDAO {

	List<MainDTO> getMainList();

	Integer totalCountByCat(String catDomain);

	Integer totalCountByBoard(Integer boardNum);

	Integer checkBlockUser(InsertUserRoleDTO insertUserRoleDTO);

	List<BoardDTO> getBoardList(BoardSearchDTO boardSearchDTO);

	Integer insertBoard(BoardDTO boardDTO);

	Integer updateBoard(BoardDTO boardDTO);

	Integer deleteBoard(BoardDTO boardDTO);

	List<BoardDTO> searchBoardBasic(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardComplex(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardAll(BoardSearchDTO boardSearchDTO);

	void updateView(BoardDetailDTO boardDetailDTO);

	BoardDetailDTO boardDetail(BoardDetailDTO boardDetailDTO);

	List<ReplyDTO> replyList(BoardSearchDTO boardSearchDTO);

	void backUpBoard(BoardDTO boardDTO);

	void plusCountCategoryboardCnt(BoardDTO boardDTO);

	void minusCountCategoryboardCnt(BoardDTO boardDTO);



}
