
package com.project.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.ConstantUserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.ManagerDAO;
import com.project.dto.BoardDTO;
import com.project.dto.CheckRightCatDTO;
import com.project.dto.InsertUserRoleDTO;
import com.project.dto.MbSessionDTO;
import com.project.dto.ReplyDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerDAO managerDAO;

	private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

	public String insertSubManager(String catDomain, String id) {
		inputUserCheck(catDomain, id);
		InsertUserRoleDTO insertUserRoleDTO = changeUserRole(catDomain, id);
		insertUserRoleDTO.setRoleNum(UserRole.SUB_MNG.getLevel());
		Integer insertCheckCount = managerDAO.insertSubManager(insertUserRoleDTO);
		checkResult(insertCheckCount);
		return id;
	}

	public String deleteSubManager(String catDomain, String id) {
		inputUserCheck(catDomain, id);
		InsertUserRoleDTO insertUserRoleDTO = changeUserRole(catDomain, id);
		Integer insertCheckCount = managerDAO.deleteSubManager(insertUserRoleDTO);
		checkResult(insertCheckCount);
		return id;
	}

	public String insertBlockUser(String catDomain, String id) {
		inputUserCheck(catDomain, id);
		InsertUserRoleDTO insertUserRoleDTO = changeUserRole(catDomain, id);
		insertUserRoleDTO.setRoleNum(UserRole.BLOCK.getLevel());
		Integer insertCheckCount = managerDAO.insertBlockUser(insertUserRoleDTO);
		checkResult(insertCheckCount);
		return id;
	}

	private InsertUserRoleDTO changeUserRole(String catDomain, String id) {
		InsertUserRoleDTO insertUserRoleDTO = new InsertUserRoleDTO();
		insertUserRoleDTO.setCatDomain(catDomain);
		insertUserRoleDTO.setId(id);
		return insertUserRoleDTO;
	}


	public List<Integer> deleteBoardNumList(String catDomain, List<BoardDTO> list) {
		inputDeleteBoardNumListCheck(catDomain, list);
		List<Integer> deleteList = new ArrayList<>();
		Integer deleteBoardNum;
		for (BoardDTO boardDTO : list) {
			deleteBoardNum = handleBoardDTO(catDomain, boardDTO);
			deleteList.add(deleteBoardNum);
		}
		return deleteList;
	}

	// 인자값 nullCheck , 레벨 체크 , 카테고리 권한 체크
	private boolean inputDeleteBoardNumListCheck(String catDomain, List<BoardDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (list == null || catDomain == null || list.isEmpty()) {
			logger.warn("inputDeleteBoardNumListCheck access ID: {}, IP : {} insert null value list : {}", memberId,
					accessIP, list);
			throw new IllegalArgumentException("inputDeleteBoardNumListCheck insert null value memberId : " + memberId
					+ ", accessIP : " + accessIP);
		}
		Integer checkInsert = checkLevel(accessIP, memberInfo);
		boolean checkRightCat = false;
		if (isAdminLevel(checkInsert)) {
			checkRightCat = true;
		} else if (isMngLevel(checkInsert) || isSubMngLevel(checkInsert)) {
			checkRightCat = checkRightCat(catDomain, memberId);
		} else {
			logger.warn("inputDeleteBoardNumListCheck in aberrant value checkInsert : {}", checkInsert);
			checkRightCat = false;
		}
		return checkRightCat;
	}

	// 기존 boardDTO는 백업 하고 boardDTO.get해서 하나씩 삭제
	public Integer handleBoardDTO(String catDomain, BoardDTO boardDTO) {
		Integer deleteBoardNum = null;
		String checkCatDomain = boardDTO.getCatDomain();
		if (checkCatDomain.equals(catDomain)) {
			managerDAO.deleteBoardBackup(boardDTO);
			Integer insertCheckCount = managerDAO.deleteBoardNumList(boardDTO);
			checkResult(insertCheckCount);
			deleteBoardNum = boardDTO.getBoardNum();
		}
		return deleteBoardNum;
	}

	public List<Integer> deleteRplNumList(String catDomain, Integer boardNum, List<ReplyDTO> list) {
		inputDeleteRplNumListCheck(catDomain, boardNum, list);
		List<Integer> deleteList = new ArrayList<>();
		Integer deleteRplNum;
		for (ReplyDTO deleteReplyDTO : list) {
			System.err.println(deleteReplyDTO);
			managerDAO.deleteReplyBackup(deleteReplyDTO);
			deleteRplNum = handleReplyDTO(boardNum, deleteReplyDTO);
			deleteList.add(deleteRplNum);
		}
		return deleteList;
	}

	// ReplyDTO 뎁스 확인 후 삭제 또는 rplCn == null 변경 프론트에서 안보이게 처리
	private Integer handleReplyDTO(Integer boardNum, ReplyDTO deleteReplyDTO) {
		deleteReplyDTO.setBoardNum(boardNum);
		Integer depth = deleteReplyDTO.getDepth();
		Integer deleteNum = null;

		if (depth == ConstantConfig.checkDepthLevel) {
			deleteNum = checkDepthOne(deleteReplyDTO);
		} else if (depth > ConstantConfig.checkDepthLevel) {
			deleteNum = checkDepthOneGreater(deleteReplyDTO);
		} else {
			logger.error("deleteRplNumList exact depth : {}", depth);
			throw new UnknownException("예기치 못한 상태");
		}
		return deleteNum;
	}

	// 대댓글 기준 / 기존 DTO는 백업 이후 getRplNum를 List 저장 이용 null 변경
	private Integer checkDepthOneGreater(ReplyDTO replyDTO) {
		Integer insertCheckCount = managerDAO.setRplCnNull(replyDTO);
		checkResult(insertCheckCount);
		Integer deleteNum = replyDTO.getRplNum();
		return deleteNum;
	}

	// 댓글 기준 / 기존 DTO는 백업 이후 getRplNum를 List 저장 이용 삭제
	private Integer checkDepthOne(ReplyDTO replyDTO) {
		Integer insertCheckCount = managerDAO.deleteReplyNum(replyDTO);
		checkResult(insertCheckCount);
		Integer deleteNum = replyDTO.getRplNum();
		return deleteNum;
	}

	// rplnum 인자값 확인 권한 확인
	private boolean inputDeleteRplNumListCheck(String catDomain, Integer boardNum, List<ReplyDTO> list) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (list == null || catDomain == null || boardNum == null || list.isEmpty()) {
			logger.warn("deleteBoardNumList access ID: {}, IP : {} "
					+ "insert null value catDomain : {}, boardNum : {}, list : {}", memberId, accessIP, list);
			throw new IllegalArgumentException(
					"inputdeleteRplNumListCheck insert null value memberId : " + memberId + ", accessIP : " + accessIP);
		}
		Integer checkInsert = checkLevel(accessIP, memberInfo);
		boolean checkRightCat = false;
		if (isAdminLevel(checkInsert)) {
			checkRightCat = true;
		} else if (isMngLevel(checkInsert) || isSubMngLevel(checkInsert)) {
			checkRightCat = checkRightCat(catDomain, memberId);
		} else {
			logger.warn("inputDeleteRplNumListCheck in aberrant value checkInsert : {}", checkInsert);
			checkRightCat = false;
		}
		return checkRightCat;
	}

	// 인자값 확인 or 권한 확인
	private boolean inputUserCheck(String catDomain, String id) {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO memberInfo = SessionConfig.MbSessionDTO();
		String memberId = memberInfo.getId();
		if (catDomain == null || id == null) {
			logger.warn(
					"inputChangeSubManagerCheck access ID: {}, IP : {} insert null value " + "catDomain : {}, id : {}",
					memberId, accessIP, catDomain, id);
			throw new IllegalArgumentException(
					"inputChangeSubManagerCheck insert null value memberId : " + memberId + ", accessIP : " + accessIP);
		}

		Integer checkInsert = checkLevel(accessIP, memberInfo);
		boolean checkRightCat = false;
		if (isAdminLevel(checkInsert)) {
			checkRightCat = true;
		} else if (isMngLevel(checkInsert)) {
			checkRightCat = checkRightCat(catDomain, memberId);
		} else {
			logger.warn("inputChangeSubManagerCheck in aberrant value checkInsert : {}", checkInsert);
			checkRightCat = false;
		}
		return checkRightCat;

	}

	// MNG catDomain 체크
	private Boolean checkRightCat(String catDomain, String memberId) {
		CheckRightCatDTO checkRightCatDTO = new CheckRightCatDTO();
		checkRightCatDTO.setCatDomain(catDomain);
		checkRightCatDTO.setId(memberId);
		Integer checkRightCat = managerDAO.selectMng(checkRightCatDTO);
		if (checkRightCat >= UserRole.SUB_MNG.getLevel()) {
			return true;
		} else {
			logger.warn("not found manager access ID : {}", memberId);
			throw new IllegalArgumentException("not found manager check RoleUser or catDomain");
		}
	}

	// 권한 체크
	private Integer checkLevel(String accessIP, MbSessionDTO memberInfo) {
		Integer accessRoleNum = memberInfo.getRoleList().get(0).getRoleNum();
		String memberId = memberInfo.getId();
		Integer checkLevel = null;
		if (isAdminLevel(accessRoleNum)) {
			checkLevel = accessRoleNum;
			logger.info("admin user : {} , IP : {}", memberId, accessIP);
		} else if (isMngLevel(accessRoleNum)) {
			checkLevel = accessRoleNum;
			logger.info("mng user : {} , IP : {}", memberId, accessIP);
		} else if (isSubMngLevel(accessRoleNum)) {
			checkLevel = accessRoleNum;
			logger.info("subMng user : {} , IP : {}", memberId, accessIP);
			throw new IllegalArgumentException("subMng level access. Please check");
		} else if (isBasicLevel(accessRoleNum)) {
			logger.warn("basic user : {} , IP : {}", memberId, accessIP);
			throw new IllegalArgumentException("basic level access. Please check");
		} else if (isBlockLevel(accessRoleNum)) {
			logger.warn("block user : {} , IP : {}", memberId, accessIP);
			throw new IllegalArgumentException("basic level access. Please check");
		} else if (isLessLevel(accessRoleNum)) {
			logger.error("unknown status, access user : {} , IP : {}", memberId, accessIP);
			throw new UnknownException("비정삭적인 접근 레벨을 갖고 있습니다.");
		} else if (accessRoleNum == null) {
			logger.warn("access guest IP : {}", accessIP);
			throw new IllegalArgumentException("have not level user or guest User. Please check");
		} else {
			logger.error("unknown status, access IP : {}", accessIP);
			throw new UnknownException("예기치 못한 접급입니다.");
		}
		return checkLevel;
	}

	// 권한 레벨 확인
	private boolean isAdminLevel(Integer accessRoleNum) {
		boolean check = UserRole.ADMIN.getLevel() == accessRoleNum;
		return check;
	}

	private boolean isMngLevel(Integer accessRoleNum) {
		boolean check = (UserRole.ADMIN.getLevel() > accessRoleNum && accessRoleNum > UserRole.SUB_MNG.getLevel());
		return check;
	}

	private boolean isSubMngLevel(Integer accessRoleNum) {
		boolean check = (UserRole.MNG.getLevel() > accessRoleNum && accessRoleNum > UserRole.BASIC.getLevel());
		return check;
	}

	private boolean isBasicLevel(Integer accessRoleNum) {
		boolean check = (UserRole.SUB_MNG.getLevel() > accessRoleNum && accessRoleNum > UserRole.BLOCK.getLevel());
		return check;
	}

	private boolean isBlockLevel(Integer accessRoleNum) {
		boolean check = (UserRole.BLOCK.getLevel() == accessRoleNum);
		return check;
	}

	private boolean isLessLevel(Integer accessRoleNum) {
		boolean check = (UserRole.BLOCK.getLevel() > accessRoleNum);
		return check;
	}

	// 인서트 값이 정상이 아니면 에러
	private boolean checkResult(Integer insertCheckCount) {
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

}
