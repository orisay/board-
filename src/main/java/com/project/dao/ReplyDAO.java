package com.project.dao;

import com.project.dto.ReplyDTO;

public interface ReplyDAO {

	Integer boardCountPlus(Integer rplplus);

	Integer replyInsert(ReplyDTO replyDTO);

	Integer replyUpdate(ReplyDTO replyDTO);

	Integer boardCountMinus();

	Integer replyDelete(Integer rplNum);

	void replyBackUp(ReplyDTO replyDTO);

}
