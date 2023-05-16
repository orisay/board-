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

	public Integer replyInsert(Integer boardNum, ReplyDTO replyDTO, Integer rplNum) {
		if (rplNum != null) {
			replyDTO.setParentRpl(rplNum);
		}
		replyDTO.setBoardNum(boardNum);
		replyDAO.boardCountPlus(ConstantConfig.rplPlus);
		Integer replyInsert = replyDAO.replyInsert(replyDTO);
		return replyInsert;
	}

	public Integer replyUpdate(Integer rplNum, ReplyDTO replyDTO) {
		replyDTO.setRplNum(rplNum);
		Integer replyUpdate = replyDAO.replyUpdate(replyDTO);
		return replyUpdate;
	}

	public void replyDelete(Integer rplNum) {
		replyDAO.boardCountMinus();
		replyDAO.replyDelete(rplNum);

	}

}
