package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.ReplyDTO;
@Mapper
public interface ReplyDAO {

	Integer countPlusBoard(Integer boardNum);

	Integer insertReply(ReplyDTO replyDTO);

	Integer updateReply(ReplyDTO replyDTO);

	Integer countMinusBoard(Integer boardNum);

	void backUpReply(ReplyDTO replyDTO);

	Integer deleteReply(Integer rplNum);

	Integer deleteRplCnNUll(Integer rplNum);


}
