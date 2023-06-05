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

	Integer deleteGuestReply(ReplyDTO replyDTO);

	Integer deleteGuestRplCnNUll(ReplyDTO replyDTO);

	Integer deleteMemberReply(ReplyDTO replyDTO);

	Integer deleteMemberRplCnNUll(ReplyDTO replyDTO);


}
