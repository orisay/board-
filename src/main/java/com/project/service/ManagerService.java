
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
import com.project.dto.CheckRightCatDTO;
import com.project.dto.MbSessionDTO;
import com.project.dto.ReplyDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerDAO managerDAO;

	private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

	// 서브 매니저 변경
	public String changeSubManager(String catDomain, String id) {
		boolean insertCheck = inputChangeSubManagerCheck(catDomain, id);
		MbSessionDTO mbSessionDTO = new MbSessionDTO();
		Integer insertCheckCount = null;
		String successMesg = null;
		if (insertCheck) {
			mbSessionDTO.setCatDomain(catDomain);
			mbSessionDTO.setRoleNum(UserRole.SUB_MNG.getLevel());
			mbSessionDTO.setId(id);
			insertCheckCount = managerDAO.changeSubManager(mbSessionDTO);
		}
		if (insertCheckCount == 1) {
			successMesg = id;
		} else {
			logger.error("DB error or Fatal error");
			throw new UnknownException("에상치 못한 값이 반환되었습니다.");
		}
		return successMesg;
	}

	// boardDTO 전체를 리스트로 받아서 삭제 기능
	public List<Integer> deleteBoardNumList(String catDomain, List<BoardDTO> list) {
		boolean insertCheck = inputdeleteBoardNumListCheck(catDomain, list);
		List<Integer> deleteList = new ArrayList<>();
		if (insertCheck) {
			for (BoardDTO boardDTO : list) {
				deleteList = handleBoardDTO(catDomain, boardDTO, deleteList);
			}
		}
		return deleteList;

	}

	// ReplyDTO를 리스트로 받아서 삭제 기능
	public List<Integer> deleteRplNumList(String catDomain, Integer boardNum, List<ReplyDTO> list) {
		boolean insertCheck = inputdeleteRplNumListCheck(catDomain, boardNum, list);
		List<Integer> deleteList = new ArrayList<>();
		if (insertCheck) {
			for (ReplyDTO deleteReplyDTO : list) {
				Integer deleteBoardNum = handleReplyDTO(boardNum, deleteReplyDTO);
				deleteList.add(deleteBoardNum);
			}
		}
		return deleteList;
	}

	//인자값 nullCheck , 레벨 체크 , 카테고리 권한 체크
	private boolean inputChangeSubManagerCheck(String catDomain, String id) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (catDomain == null || id == null) {
			logger.warn("inputChangeSubManagerCheck access ID: {}, IP : {} insert null value "
					+ "catDomain : {}, id : {}", memberId, accessIP, catDomain, id);
			throw new IllegalArgumentException(
					"inputChangeSubManagerCheck insert null value memberId : "
							+ memberId + ", accessIP : " + accessIP);
		}
		boolean checkInsert = checkLevel(accessIP, memberInfo);
		boolean checkRightCat = checkRightCat(catDomain, memberId);
		if (checkInsert && checkRightCat) {
			return true;
		} else {
			logger.error("예상치 못한 상태가 발생했습니다.");
			throw new UnknownException("전혀 예기치 못한 상태가 발생했습니다.");
		}
	}

	//인자값 nullCheck , 레벨 체크 , 카테고리 권한 체크
	private boolean inputdeleteBoardNumListCheck(String catDomain, List<BoardDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (list == null || catDomain == null || list.isEmpty()) {
			logger.warn("deleteBoardNumList access ID: {}, IP : {} insert null value list : {}"
					, memberId, accessIP, list);
			throw new IllegalArgumentException(
					"deleteBoardNumList insert null value memberId : "
			+ memberId + ", accessIP : " + accessIP);
		}
		Boolean checkInsert = checkLevel(accessIP, memberInfo);
		Boolean checkRightCat = checkRightCat(catDomain, memberId);
		if (checkInsert && checkRightCat) {
			return true;
		} else {
			logger.error("예상치 못한 상태가 발생했습니다.");
			throw new UnknownException("전혀 예기치 못한 상태가 발생했습니다.");
		}
	}

	//인자값 nullCheck , 레벨 체크 , 카테고리 권한 체크
	private boolean inputdeleteRplNumListCheck(String catDomain, Integer boardNum, List<ReplyDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (list == null || catDomain == null || boardNum == null || list.isEmpty()) {
			logger.warn("deleteBoardNumList access ID: {}, IP : {} "
					+ "insert null value catDomain : {}, boardNum : {}, list : {}"
					, memberId, accessIP, list);
			throw new IllegalArgumentException(
					"inputdeleteRplNumListCheck insert null value memberId : "
			+ memberId + ", accessIP : " + accessIP);
		}
		Boolean checkInsert = checkLevel(accessIP, memberInfo);
		Boolean checkRightCat = checkRightCat(catDomain, memberId);
		if (checkInsert && checkRightCat) {
			return true;
		} else {
			logger.error("예상치 못한 상태가 발생했습니다.");
			throw new UnknownException("전혀 예기치 못한 상태가 발생했습니다.");
		}
	}

	// 기존 boardDTO는 백업 하고 boardDTO.get해서 하나씩 삭제
	public List<Integer> handleBoardDTO(String catDomain, BoardDTO boardDTO, List<Integer> deleteList) {
		Integer deleteBoardNum = boardDTO.getBoardNum();
		String checkCatDomain = boardDTO.getCatDomain();
		if (checkCatDomain.equals(catDomain)) {
			deleteList.add(deleteBoardNum);
			managerDAO.deleteBoardBackup(boardDTO);
			Integer insertCheckCount = managerDAO.deleteBoardNumList(deleteBoardNum);
			insertErrorCheck(insertCheckCount);
		}
		return deleteList;
	}

	// ReplyDTO 뎁스 확인 후 삭제 또는 rplCn == null 변경 프론트에서 안보이게 처리
	private Integer handleReplyDTO(Integer boardNum, ReplyDTO deleteReplyDTO) {
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
		managerDAO.deleteReplyBackup(deleteReplyDTO);
		returnValue = deleteReplyDTO.getRplNum();
		Integer insertCheckCount = managerDAO.deleteReplyNum(rplNum);
		insertErrorCheck(insertCheckCount);
		return returnValue;
	}

	// 기존 DTO는 백업 이후 getRplNum를 List 저장 이용 삭제
	private Integer checkDepthOne(ReplyDTO deleteReplyDTO, Integer returnValue) {
		managerDAO.deleteReplyBackup(deleteReplyDTO);
		deleteReplyDTO.setRplCn(null);
		returnValue = deleteReplyDTO.getRplNum();
		Integer insertCheckCount = managerDAO.setRplCnNull(deleteReplyDTO);
		insertErrorCheck(insertCheckCount);
		return returnValue;
	}

	// 인서트 값이 정상이 아니면 에러
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
	private boolean checkLevel(String accessIP, MbSessionDTO memberInfo) {
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

	private Boolean checkRightCat(String catDomain, String Id) {
		CheckRightCatDTO checkRightCatDTO = new CheckRightCatDTO();
		checkRightCatDTO.setCatDomain(catDomain);
		checkRightCatDTO.setId(Id);
		System.err.println(checkRightCatDTO);
		Integer checkRightCat = managerDAO.selectMng(checkRightCatDTO);
		System.err.println(checkRightCat);
		if (checkRightCat > UserRole.SUB_MNG.getLevel()) {
			return true;
		} else {
			logger.warn("not found manager access ID : {}", Id);
			throw new IllegalArgumentException("not found manager check RoleUser or catDomain");
		}
	}

}
