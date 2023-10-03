package com.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.project.dto.reply.ReplyDTO;
@Mapper
public interface ReplyDAO {

	Integer countPlusBoard(Integer boardNum);

	Integer createParentReply(ReplyDTO replyDTO);

	Integer createChildReReply(ReplyDTO replyDTO);

	Integer updateGuestReply(ReplyDTO replyDTO);

	Integer updateMemberReply(ReplyDTO replyDTO);

	Integer countMinusBoard(Integer boardNum);

	void backupReply(ReplyDTO replyDTO);

	Integer deleteGuestReply(ReplyDTO replyDTO);

	Integer deleteGuestRplCnNUll(ReplyDTO replyDTO);

	Integer deleteMemberReply(ReplyDTO replyDTO);

	Integer deleteMemberRplCnNUll(ReplyDTO replyDTO);

}
