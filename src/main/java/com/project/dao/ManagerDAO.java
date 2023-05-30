package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.CategoryDTO;
import com.project.dto.CheckRightCatDTO;
import com.project.dto.ReplyDTO;

@Mapper
public interface ManagerDAO {

	Integer changeSubManager(CategoryDTO categoryDTO);

	void deleteReplyBackup(ReplyDTO deleteReplyDTO);

	Integer deleteBoardNumList(Integer deleteBoardNum);

	void deleteBackupBoard(BoardDTO boardDTO);

	Integer deleteReplyNum(Integer rplNum);

	Integer setRplCnNull(ReplyDTO deleteReplyDTO);

	Integer selectMng(CheckRightCatDTO checkRightCatDTO);






}
