package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.ReplyDTO;
@Mapper
public interface ReplyDAO {

	Integer countPlusBoard(Integer rplplus);

	Integer insertReply(ReplyDTO replyDTO);

	Integer updateReply(ReplyDTO replyDTO);

	Integer countMinusBoard();

	void backUpReply(ReplyDTO replyDTO);

	Integer deleteReply(Integer rplNum);

	Integer deleteRplCnNUll(Integer rplNum);


}
