package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.config.ConstantConfig;
import com.project.dao.ReplyDAO;
import com.project.dto.ReplyDTO;

@Service
public class ReplyService {
	@Autowired
	ReplyDAO replyDAO;


	public Integer replyInsert(ReplyDTO replyDTO, Integer rplNum) {
		if (rplNum!=null) {
			replyDTO.setParentRpl(rplNum);
		}
		Integer replyInsert = replyDAO.boardCountPlus(ConstantConfig.rplPlus);
		replyDAO.replyInsert(replyDTO);
		return replyInsert;
	}

	public Integer replyUpdate(ReplyDTO replyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public void replyDelete(Integer rplNum) {
		// TODO Auto-generated method stub

	}




}
