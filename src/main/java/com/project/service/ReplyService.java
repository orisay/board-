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
	ReplyDAO replyDAO;

	private static final Logger logger = LogManager.getLogger(ReplyService.class);

	// 댓글 작성
	@Transactional
	public Integer insertReply(Integer boardNum, ReplyDTO replyDTO, Integer rplNum) throws UnknownException {
		String id = acessRight();
		//부모 댓글 여부 확인
		if (rplNum != null) {
			replyDTO.setParentRpl(rplNum);
		}

		if (boardNum != null && replyDTO != null) {
			replyDTO.setId(id);
			replyDTO.setBoardNum(boardNum);
		} else if (boardNum == null || replyDTO == null) {
			logger.warn("insertReply access user : {} , null values boardNum : {}, replyDTO : {}"
					, id, boardNum, replyDTO);
			throw new UnknownException("인자 값 중에 null 존재");
		}

		replyDAO.countPlusBoard(ConstantConfig.rplPlus);
		Integer replyInsert = replyDAO.insertReply(replyDTO);
		return replyInsert;
	}

	// 댓글 수정
	@Transactional
	public Integer updateReply(Integer rplNum, ReplyDTO replyDTO) throws UnknownException {
		String id = acessRight();

		if (rplNum != null && replyDTO != null) {
			replyDTO.setRplNum(rplNum);
			replyDTO.setId(id);
		} else {
			logger.warn("updateReply access user : {} , null values boardNum : {}, replyDTO : {}"
					, id, rplNum,replyDTO);
			throw new UnknownException("인자 값 중에 null 존재");
		}
		Integer replyUpdate = replyDAO.updateReply(replyDTO);
		return replyUpdate;
	}

	// 댓글 삭제
	@Transactional
	public Integer deleteReply(Integer rplNum, ReplyDTO replyDTO) throws UnknownException {
		String id = acessRight();

		if (rplNum != null && replyDTO != null) {
			System.err.println(replyDTO);
			replyDAO.countMinusBoard();
			replyDAO.backUpReply(replyDTO);
		} else {
			logger.warn("deleteReply access user : {} , null values boardNum : {}, replyDTO : {}"
					, id, rplNum, replyDTO);
			throw new UnknownException("인자 값 중에 null 존재");
		}
		Integer replyDelete = replyDAO.deleteReply(rplNum);
		return replyDelete;
	}

	// guest인지 회원인지 확인
	private String acessRight() throws UnknownException {
		String gusetId = IPConfig.getIp(SessionConfig.getSession());
		String memberId = SessionConfig.getMbDTO().getId();
		String mesg = "";

		if (memberId != null) {
			mesg = memberId;
		} else if (memberId == null && gusetId != null) {
			mesg = gusetId;
		} else if (gusetId == null) {
			logger.fatal("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		}
		return mesg;
	}
}
