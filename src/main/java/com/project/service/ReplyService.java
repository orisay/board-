package com.project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.ReplyDAO;
import com.project.dto.ReplyDTO;
import com.project.exception.UnknownException;

@Service
public class ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	private static final Logger logger = LogManager.getLogger(ReplyService.class);

	// 댓글 작성
	@Transactional
	public Integer insertReply(Integer boardNum, ReplyDTO replyDTO, Integer rplNum) {
		String id = getAccessRight();
		if (boardNum == null || replyDTO == null) {
			logger.warn("insertReply access user : {} , null values boardNum : {}, replyDTO : {}", id, boardNum,
					replyDTO);
			throw new IllegalArgumentException(
					"ReplyService insertReply null value boardNum : " + boardNum + " replyDTO : " + replyDTO);
		} else if (rplNum != null) {
			replyDTO.setParentRpl(rplNum);
		}

		replyDTO.setId(id);
		replyDTO.setBoardNum(boardNum);
		replyDAO.countPlusBoard(boardNum);
		Integer replyInsert = replyDAO.insertReply(replyDTO);
		return replyInsert;
	}

	// 댓글 수정
	@Transactional
	public Integer updateReply(Integer rplNum, ReplyDTO replyDTO) {
		String id = getAccessRight();
		if (rplNum == null || replyDTO == null) {
			logger.warn("updateReply access user : {} , null values boardNum : {}, replyDTO : {}", id, rplNum,
					replyDTO);
			throw new IllegalArgumentException(
					"ReplyService updateReply null value rplNum : " + rplNum + " replyDTO " + replyDTO);
		}
		replyDTO.setRplNum(rplNum);
		Integer replyUpdate = replyDAO.updateReply(replyDTO);
		return replyUpdate;
	}

	// 댓글 삭제
	@Transactional
	public Integer deleteReply(Integer rplNum, ReplyDTO replyDTO) {
		String id = getAccessRight();
		if (rplNum == null || replyDTO == null) {
			logger.warn("deleteReply access user : {} , null values boardNum : {}, replyDTO : {}", id, rplNum,
					replyDTO);
			throw new IllegalArgumentException(
					"ReplyService deleteReply null value rplNum : " + rplNum + " replyDTO " + replyDTO);
		}
		System.err.println(replyDTO);
		replyDAO.countMinusBoard(replyDTO.getBoardNum());
		replyDAO.backUpReply(replyDTO);
		Integer replyDelete = null;
		if (replyDTO.getDepth() == ConstantConfig.startDepth) {
			replyDelete = replyDAO.deleteReply(rplNum);
		} else if (replyDTO.getDepth() > ConstantConfig.startDepth) {
			replyDelete = replyDAO.deleteRplCnNUll(rplNum);
		}
		return replyDelete;
	}

	// guest인지 회원인지 확인
	private String getAccessRight() {
		String guestId = IPConfig.getIp(SessionConfig.getSession());
		String memberId = SessionConfig.getMbDTO().getId();
		String mesg = null;

		if (guestId == null) {
			logger.error("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		} else if (guestId != null && memberId == null) {
			mesg = guestId;
		} else {
			mesg = memberId;
		}
		return mesg;

	}
}
