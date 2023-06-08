package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.CheckRightCatDTO;
import com.project.dto.MbSessionDTO;
import com.project.dto.ReplyDTO;

@Mapper
public interface ManagerDAO {

	Integer changeSubManager(MbSessionDTO mbSessionDTO);

	Integer deleteBoardNumList(Integer deleteBoardNum);

	void deleteBoardBackup(BoardDTO boardDTO);

	Integer deleteReplyNum(Integer rplNum);

	void deleteReplyBackup(ReplyDTO deleteReplyDTO);

	Integer setRplCnNull(ReplyDTO deleteReplyDTO);

	Integer selectMng(CheckRightCatDTO checkRightCatDTO);

}
