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
@Transactional
public class ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	private static final Logger logger = LogManager.getLogger(ReplyService.class);

	// 댓글 작성
	public Integer insertReply(Integer boardNum, ReplyDTO replyDTO, Integer rplNum) {
		String info = getAccessInfo();
		if (boardNum == null || replyDTO == null) {
			logger.warn("insertReply access info : {} , null values boardNum : {}, replyDTO : {}", info, boardNum,
					replyDTO);
			throw new IllegalArgumentException(
					"ReplyService insertReply null value boardNum : " + boardNum + " replyDTO : " + replyDTO);
		} else if (rplNum != null) {
			replyDTO.setParentRpl(rplNum);
		}

		replyDTO.setId(info);
		replyDTO.setBoardNum(boardNum);
		replyDAO.countPlusBoard(boardNum);
		Integer replyInsert = replyDAO.insertReply(replyDTO);
		return replyInsert;
	}

	// 댓글 수정
	public Integer updateReply(ReplyDTO replyDTO) {
		String info = getAccessInfo();
		if (replyDTO == null) {
			logger.warn("updateReply access info : {} , null values replyDTO : {}", info, replyDTO);
			throw new IllegalArgumentException("ReplyService updateReply null value replyDTO : " + replyDTO);
		}
		Integer replyUpdate;
		replyDTO.setId(info);
		String checkMember = getAccessId();
		if (checkMember.equals("guest")) {
			replyUpdate = replyDAO.updateGuestReply(replyDTO);
		} else {
			replyUpdate = replyDAO.updateMemberReply(replyDTO);
		}

		return replyUpdate;
	}

	// 댓글 삭제
	public Integer deleteReply(ReplyDTO replyDTO) {
		String info = getAccessInfo();
		if (replyDTO == null) {
			logger.warn("deleteReply access user : {} , null values  replyDTO : {}", info, replyDTO);
			throw new IllegalArgumentException("ReplyService deleteReply null value replyDTO : " + replyDTO);
		}
		replyDAO.countMinusBoard(replyDTO.getBoardNum());
		replyDAO.backUpReply(replyDTO);
		Integer replyDelete = null;
		String checkMember = getAccessId();
		System.err.println(checkMember);
		System.err.println(replyDTO);
		if (replyDTO.getParentRpl() == ConstantConfig.compare && checkMember.equals("guest")) {
			replyDelete = replyDAO.deleteGuestReply(replyDTO);
		} else if (replyDTO.getParentRpl() > ConstantConfig.compare && checkMember.equals("guest")) {
			replyDelete = replyDAO.deleteGuestRplCnNUll(replyDTO);
		} else if (replyDTO.getParentRpl() == ConstantConfig.compare && checkMember.equals("member")) {
			replyDelete = replyDAO.deleteMemberReply(replyDTO);
		} else if (replyDTO.getParentRpl() > ConstantConfig.compare && checkMember.equals("member")) {
			replyDelete = replyDAO.deleteMemberRplCnNUll(replyDTO);
		}
		return replyDelete;
	}

	// guest인지 회원인지 확인
	private String getAccessInfo() {
		String guestId = IPConfig.getIp(SessionConfig.getSession());
		String memberId = SessionConfig.MbSessionDTO().getId();
		String info = null;
		if (guestId == null) {
			logger.error("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		} else if (guestId != null && memberId == null) {
			info = guestId;
		} else {
			info = memberId;
		}
		return info;
	}

	private String getAccessId() {
		String memberId = SessionConfig.MbSessionDTO().getId();
		String checkMember = null;
		if (memberId != null) {
			checkMember = "member";
		}else {
			checkMember = "guest";
		}
		return checkMember;
	}
}
