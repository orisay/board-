package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.ConstantConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.ManagerDAO;
import com.project.dto.BoardDTO;
import com.project.dto.MbDTO;
import com.project.dto.ReplyDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class ManagerService {

	@Autowired
	ManagerDAO adminDAO;

	private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

	public String changeSubManager(String catDomain, String id) {
		return null;
	}

	// boardDTO 전체를 리스트로 받아서 삭제 기능
	public List<Integer> deleteBoardNumList(List<BoardDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbDTO memberInfo = SessionConfig.getMbDTO();
		String memberId = memberInfo.getId();
		if (list == null || list.isEmpty()) {
			logger.warn("deleteBoardNumList access ID: {}, IP : {} insert null value list : {}", memberId, accessIP,
					list);
			throw new IllegalArgumentException(
					"deleteBoardNumList insert null value memberId : " + memberId + ", accessIP : " + accessIP);
		}
		List<Integer> deleteList = new ArrayList<>();
		Boolean checkInsert = checkLevel(accessIP, memberInfo);
		if (checkInsert) {
			for (BoardDTO boardDTO : list) {
				deleteList = deleteBoardDTO(boardDTO, deleteList);
			}
		}
		return deleteList;

	}

	// 기존 boardDTO는 백업 하고 boardDTO.get해서 하나씩 삭제
	public List<Integer> deleteBoardDTO(BoardDTO boardDTO, List<Integer> deleteList) {
		Integer deleteBoardNum = boardDTO.getBoardNum();
		deleteList.add(deleteBoardNum);
		adminDAO.deleteBackupBoard(boardDTO);
		Integer insertCheckCount = adminDAO.deleteBoardNumList(deleteBoardNum);
		insertErrorCheck(insertCheckCount);
		return deleteList;
	}

	// ReplyDTO를 리스트로 받아서 삭제 기능
	public List<Integer> deleteRplNumList(Integer boardNum, List<ReplyDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbDTO memberInfo = SessionConfig.getMbDTO();
		String memberId = memberInfo.getId();
		if (boardNum == null || list == null || list.isEmpty()) {
			logger.warn("deleteRplNumList access ID : {} , IP : {} insert values boardNum : {}, list : {}", memberId,
					accessIP, boardNum, list);
			throw new IllegalArgumentException(
					"deleteRplNumList insert null values boardNum" + boardNum + ", list : " + list);
		}
		List<Integer> deleteList = new ArrayList<>();
		boolean checkInsert = checkLevel(accessIP, memberInfo);
		if (checkInsert) {
			for (ReplyDTO deleteReplyDTO : list) {
				Integer deleteBoardNum = deleteReplyDTO(boardNum, deleteReplyDTO);
				deleteList.add(deleteBoardNum);
			}
		}
		return deleteList;
	}

	// ReplyDTO 뎁스 확인 후 삭제 또는 rplCn == null 변경 프론트에서 안보이게 처리
	private Integer deleteReplyDTO(Integer boardNum, ReplyDTO deleteReplyDTO) {
		Integer deleteNum = null;
		Integer returnValue = null;
		deleteReplyDTO.setBoardNum(boardNum);
		Integer rplNum = deleteReplyDTO.getRplNum();
		Integer depth = deleteReplyDTO.getDepth();

		if (depth == ConstantConfig.checkDepthLevel) {
			deleteNum = checkDepthOne(deleteReplyDTO, returnValue);
		} else if (depth > ConstantConfig.checkDepthLevel) {
			deleteNum = checkDepthOneGreater(deleteReplyDTO, rplNum, returnValue);
		} else {
			logger.error("deleteRplNumList exact depth : {}", depth);
			throw new UnknownException("예기치 못한 상태");
		}
		return deleteNum;
	}

	// 기존 DTO는 백업 이후 getRplNum를 List 저장 이용 null 변경
	private Integer checkDepthOneGreater(ReplyDTO deleteReplyDTO, Integer rplNum, Integer returnValue) {
		adminDAO.deleteReplyBackup(deleteReplyDTO);
		returnValue = deleteReplyDTO.getRplNum();
		Integer insertCheckCount = adminDAO.deleteReplyNum(rplNum);
		insertErrorCheck(insertCheckCount);
		return returnValue;
	}

	// 기존 DTO는 백업 이후 getRplNum를 List 저장 이용 삭제
	private Integer checkDepthOne(ReplyDTO deleteReplyDTO, Integer returnValue) {
		adminDAO.deleteReplyBackup(deleteReplyDTO);
		deleteReplyDTO.setRplCn(null);
		returnValue = deleteReplyDTO.getRplNum();
		Integer insertCheckCount = adminDAO.setRplCnNull(deleteReplyDTO);
		insertErrorCheck(insertCheckCount);
		return returnValue;
	}

	// 인서트 체크
	private boolean insertErrorCheck(Integer insertCheckCount) {
		if (insertCheckCount == 1) {
			return true;
		} else if (insertCheckCount == 0) {
			logger.warn("DB is not affected");
			throw new RuntimeException("DB is not affected. Please check");
		} else {
			logger.error("DB error or Fatal error");
			throw new UnknownException("예기치 못한 상황이 발생했습니다.");
		}
	}

	// 권한 체크
	private boolean checkLevel(String accessIP, MbDTO memberInfo) {
		String accessRole = memberInfo.getRole();
		String memberId = memberInfo.getId();
		UserRole userRole = UserRole.valueOf(accessRole);
		if (isMaxLevel(userRole)) {
			return true;
		} else if (isBasicLevel(userRole)) {
			logger.warn("basic user : {} , IP : {}", memberId, accessIP);
			throw new IllegalArgumentException("basic level access. Please check");
		} else if (isLessLevel(userRole)) {
			logger.error("unknown status, access user : {} , IP : {}", memberId, accessIP);
			throw new UnknownException("비정삭적인 접근 레벨을 갖고 있습니다.");
		} else if (userRole.getLevel() == null) {
			logger.warn("access guest IP : {}", accessIP);
			throw new IllegalArgumentException("have not level user or guest User. Please check");
		} else {
			logger.error("unknown status, access IP : {}", accessIP);
			throw new UnknownException("예기치 못한 접급입니다.");
		}
	}

	private boolean isMaxLevel(UserRole userRole) {
		boolean check = (userRole.ADMIN.getLevel() >= userRole.getLevel()
				&& userRole.getLevel() > UserRole.BASIC.getLevel());
		return check;
	}

	private boolean isBasicLevel(UserRole userRole) {
		boolean check = (userRole.getLevel() == UserRole.BASIC.getLevel());
		return check;
	}

	private boolean isLessLevel(UserRole userRole) {
		boolean check = (userRole.getLevel() < UserRole.BASIC.getLevel());
		return check;
	}

}
