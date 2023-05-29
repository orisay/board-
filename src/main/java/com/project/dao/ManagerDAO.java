package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.ReplyDTO;

@Mapper
public interface ManagerDAO {

	Integer deleteBoardNumList(Integer deleteBoardNum);

	Integer setRplCnNull(ReplyDTO deleteReplyDTO);

	Integer deleteReplyNum(Integer rplNum);

	void deleteReplyBackup(ReplyDTO deleteReplyDTO);

	void deleteBackupBoard(BoardDTO boardDTO);

}
