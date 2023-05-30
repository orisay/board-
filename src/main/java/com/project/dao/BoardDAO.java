package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.BoardSearchSpecialDTO;
import com.project.dto.ReplyDTO;
import com.project.dto.mainDTO;

@Mapper
public interface BoardDAO {

	List<mainDTO> getMainList();

	Integer totalCountByCat(String catDomain);

	Integer totalCountByBoard(String boardNum);

	List<BoardDTO> getBoardList(BoardSearchDTO boardSearchDTO);

	Integer insertBoard(BoardDTO boardDTO);

	Integer updateBoard(BoardDTO boardDTO);

	Integer deleteBoard(Integer boardNum);

	List<BoardDTO> searchBoardBasic(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardComplex(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> searchBoardAll(BoardSearchDTO boardSearchDTO);

	void updateView(BoardDetailDTO boardDetailDTO);

	BoardDetailDTO boardDetail(BoardDetailDTO boardDetailDTO);

	List<ReplyDTO> replyList(BoardSearchDTO boardSearchDTO);

	void backUpBoard(BoardDTO boardDTO);

	void totalCountPlusCat(String catDomain);

	void totalCountMinusCat(BoardDTO boardDTO);






}
