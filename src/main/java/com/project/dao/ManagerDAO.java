package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.board.BoardDTO;
import com.project.dto.category.CheckRightCatDTO;
import com.project.dto.mb.InsertUserRoleDTO;
import com.project.dto.reply.ReplyDTO;

@Mapper
public interface ManagerDAO {

	Integer selectMng(CheckRightCatDTO checkRightCatDTO);

	Integer createSubManager(InsertUserRoleDTO insertUserRoleDTO);

	Integer deleteSubManager(InsertUserRoleDTO insertUserRoleDTO);

	Integer insertBlockUser(InsertUserRoleDTO insertUserRoleDTO);

	void deleteBoardBackup(BoardDTO boardDTO);

	Integer deleteBoardNumList(BoardDTO boardDTO);

	void deleteReplyBackup(ReplyDTO replyDTO);

	Integer deleteReplyNum(ReplyDTO replyDTO);

	Integer setRplCnNull(ReplyDTO replyDTO);

}
