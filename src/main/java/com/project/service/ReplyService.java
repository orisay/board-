package com.project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.constant.ConstantConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.ReplyDAO;
import com.project.dto.reply.ReplyDTO;
import com.project.exception.NotFoundException;
import com.project.exception.UnknownException;

@Service
@Transactional
public class ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	private static final Logger logger = LogManager.getLogger(ReplyService.class);

	// 댓글 작성
	public String insertParentReply(Integer boardNum, ReplyDTO replyDTO) {
		String userInfo = getAccessInfo();
		ReplyDTO handleReplyDTO = handleReplyDTOByinsertParentReply(boardNum, userInfo, replyDTO);
		Integer insertCheckCount = replyDAO.insertParentReply(handleReplyDTO);
		String resultMesg = checkResultByInteger(insertCheckCount, userInfo);
		if (ConstantConfig.SUCCESS_MESG.equals(resultMesg)) {
			replyDAO.countPlusBoard(boardNum);
		}
		return resultMesg;
	}

	// 대댓글 작성
	public String insertChildReReply(Integer boardNum, Integer rplNum, ReplyDTO replyDTO) {
		String userInfo = getAccessInfo();
		ReplyDTO handleReplyDTO = handleReplyDTOByinsertChildReReply(boardNum, rplNum, userInfo, replyDTO);
		Integer insertCheckCount = replyDAO.insertChildReReply(handleReplyDTO);
		String resultMesg = checkResultByInteger(insertCheckCount, userInfo);
		if (ConstantConfig.SUCCESS_MESG.equals(resultMesg)) {
			replyDAO.countPlusBoard(boardNum);
		}
		return resultMesg;
	}

	// 댓글 수정
	public String updateReply(ReplyDTO replyDTO) {
		String userInfo = getAccessInfo();
		Integer insertCheckCount = handleUpdateReply(userInfo, replyDTO);
		String resultMesg = checkResultByInteger(insertCheckCount, userInfo);
		return resultMesg;
	}

	// 댓글 삭제
	public String deleteReply(ReplyDTO replyDTO) {
		String userInfo = getAccessInfo();
		Integer insertCheckCount = handleDeleteReply(replyDTO);
		String resultMesg = checkResultByInteger(insertCheckCount, userInfo);
		if (ConstantConfig.SUCCESS_MESG.equals(resultMesg)) {
			replyDAO.countMinusBoard(replyDTO.getBoardNum());
			replyDAO.backUpReply(replyDTO);
		}
		return resultMesg;
	}

	// 댓글 핸들링.
	private ReplyDTO handleReplyDTOByinsertParentReply(Integer boardNum, String memberInfo, ReplyDTO replyDTO) {
		replyDTO.setParentRpl(ConstantConfig.insertStartNum);
		replyDTO.setCreator(memberInfo);
		replyDTO.setBoardNum(boardNum);
		replyDTO.setDepth(ConstantConfig.START_NUM);
		return replyDTO;
	}

	// 대댓글 핸들링
	private ReplyDTO handleReplyDTOByinsertChildReReply(Integer boardNum, Integer rplNum, String memberInfo,
			ReplyDTO replyDTO) {
		replyDTO.setParentRpl(rplNum);
		replyDTO.setCreator(memberInfo);
		replyDTO.setBoardNum(boardNum);
		return replyDTO;
	}

	// 댓글 수정(회원,게스트 따라 로직 변경)
	private Integer handleUpdateReply(String userInfo, ReplyDTO replyDTO) {
		String checkMember = getAccessId();
		Integer insertCheckCount = null;
		if (ConstantConfig.GUEST.equals(checkMember)) {
			insertCheckCount = replyDAO.updateGuestReply(replyDTO);
		} else if (ConstantConfig.MEMBER.equals(checkMember)) {
			insertCheckCount = replyDAO.updateMemberReply(replyDTO);
		} else {
			logger.error("access user : {} , Guard Code", checkMember);
			throw new UnknownException("댓글 수정 중 예기치 못한 상태가 발생했습니다.");
		}
		return insertCheckCount;
	}

	// 회원, 게스트 구별하고 부모댓글 유무 따라 로직 변경
	private Integer handleDeleteReply(ReplyDTO replyDTO) {
		String checkMember = getAccessId();
		Integer insertCheckCount = null;
		if (replyDTO.getParentRpl() == ConstantConfig.COMPARE && ConstantConfig.GUEST.equals(checkMember)) {
			insertCheckCount = replyDAO.deleteGuestReply(replyDTO);
		} else if (replyDTO.getParentRpl() > ConstantConfig.COMPARE && ConstantConfig.GUEST.equals(checkMember)) {
			insertCheckCount = replyDAO.deleteGuestRplCnNUll(replyDTO);
		} else if (replyDTO.getParentRpl() == ConstantConfig.COMPARE && ConstantConfig.MEMBER.equals(checkMember)) {
			insertCheckCount = replyDAO.deleteMemberReply(replyDTO);
		} else if (replyDTO.getParentRpl() > ConstantConfig.COMPARE && ConstantConfig.MEMBER.equals(checkMember)) {
			insertCheckCount = replyDAO.deleteMemberRplCnNUll(replyDTO);
		}
		return insertCheckCount;
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
			logger.info("access IP : {}", guestId);
			info = guestId;
		} else {
			logger.info("access IP : {}, ID : {} ", guestId, memberId);
			info = memberId;
		}
		return info;
	}

	// guest , member 구별
	private String getAccessId() {
		String memberId = SessionConfig.MbSessionDTO().getId();
		String checkMember = null;
		if (memberId != null) {
			checkMember = ConstantConfig.MEMBER;
		} else {
			checkMember = ConstantConfig.GUEST;
		}
		return checkMember;
	}

	// DB 결과값 확인
	private String checkResultByInteger(Integer insertCheckCount, String user) {
		String resultMesg = null;
		if (insertCheckCount == ConstantConfig.SUCCESS_COUNT) {
			resultMesg = ConstantConfig.SUCCESS_MESG;
		} else if (insertCheckCount == ConstantConfig.FALSE_COUNT) {
			resultMesg = ConstantConfig.FALSE_MESG;
		} else {
			logger.error("access IP : {}, unknown status", user);
			throw new NotFoundException("DB is not affected. Please check.");
		}
		return resultMesg;
	}
}
