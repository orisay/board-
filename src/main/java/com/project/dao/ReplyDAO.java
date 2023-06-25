package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.reply.ReplyDTO;
@Mapper
public interface ReplyDAO {

	Integer countPlusBoard(Integer boardNum);

	Integer insertParentReply(ReplyDTO replyDTO);

	Integer insertChildReReply(ReplyDTO replyDTO);

	Integer updateGuestReply(ReplyDTO replyDTO);

	Integer updateMemberReply(ReplyDTO replyDTO);

	Integer countMinusBoard(Integer boardNum);

	void backUpReply(ReplyDTO replyDTO);

	Integer deleteGuestReply(ReplyDTO replyDTO);

	Integer deleteGuestRplCnNUll(ReplyDTO replyDTO);

	Integer deleteMemberReply(ReplyDTO replyDTO);

	Integer deleteMemberRplCnNUll(ReplyDTO replyDTO);

}
