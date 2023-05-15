package com.project.dao;

import com.project.dto.ReplyDTO;

public interface ReplyDAO {

	Integer boardCountPlus(Integer rplplus);

	void replyInsert(ReplyDTO replyDTO);

}
