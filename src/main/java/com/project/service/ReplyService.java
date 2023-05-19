package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.dao.ReplyDAO;
import com.project.dto.ReplyDTO;

@Service
public class ReplyService {
	@Autowired
	ReplyDAO replyDAO;

	// 댓글 작성
	@Transactional
	public Integer replyInsert(Integer boardNum, ReplyDTO replyDTO, Integer rplNum) {
		if (rplNum != null) {
			replyDTO.setParentRpl(rplNum);
		}
		replyDTO.setBoardNum(boardNum);
		replyDAO.boardCountPlus(ConstantConfig.rplPlus);
		Integer replyInsert = replyDAO.replyInsert(replyDTO);
		return replyInsert;
	}

	// 댓글 수정
	@Transactional
	public Integer replyUpdate(Integer rplNum, ReplyDTO replyDTO) {
		replyDTO.setRplNum(rplNum);
		Integer replyUpdate = replyDAO.replyUpdate(replyDTO);
		return replyUpdate;
	}

	// 댓글 삭제
	@Transactional
	public Integer replyDelete(Integer rplNum, ReplyDTO replyDTO) {
		replyDAO.boardCountMinus();
		System.err.println(replyDTO);
		replyDAO.replyBackUp(replyDTO);
		Integer replyDelete = replyDAO.replyDelete(rplNum);
		return replyDelete;
	}

}
