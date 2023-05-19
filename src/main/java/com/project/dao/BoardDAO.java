package com.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.BoardDetailDTO;
import com.project.dto.BoardSearchDTO;
import com.project.dto.ReplyDTO;
import com.project.dto.mainDTO;

@Mapper
public interface BoardDAO {

	List<mainDTO> getMainList();

	Integer categoryBoardCount(String cat);

	List<BoardDTO> boardList(BoardSearchDTO boardSearchDTO);

	Integer boardInsert(BoardDTO boardDTO);

	Integer boardUpdate(BoardDTO boardDTO);

	Integer boardDelete(Integer boardNum);

	List<BoardDTO> boardBasicSearch(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> boardComplexSearch(BoardSearchDTO boardSearchDTO);

	List<BoardDTO> boardAllSearch(BoardSearchDTO boardSearchDTO);

	void viewUpdate(BoardDetailDTO boardDetailDTO);

	BoardDetailDTO board(BoardDetailDTO boardDetailDTO);

	List<ReplyDTO> replyList(BoardSearchDTO boardSearchDTO);

	void boardBackUp(BoardDTO boardDTO);



}
