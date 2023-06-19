package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.BoardDTO;
import com.project.dto.InsertUserRoleDTO;
import com.project.dto.CheckRightCatDTO;
import com.project.dto.ReplyDTO;

@Mapper
public interface ManagerDAO {

	Integer selectMng(CheckRightCatDTO checkRightCatDTO);

	Integer insertSubManager(InsertUserRoleDTO insertUserRoleDTO);

	Integer deleteSubManager(InsertUserRoleDTO insertUserRoleDTO);

	Integer insertBlockUser(InsertUserRoleDTO insertUserRoleDTO);

	void deleteBoardBackup(BoardDTO boardDTO);

	Integer deleteBoardNumList(BoardDTO boardDTO);

	void deleteReplyBackup(ReplyDTO replyDTO);

	Integer deleteReplyNum(ReplyDTO replyDTO);

	Integer setRplCnNull(ReplyDTO replyDTO);


}
