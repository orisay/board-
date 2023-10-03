package com.project.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.constant.ConstantConfig;
import com.project.common.constant.UserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.CategoryDAO;
import com.project.dto.board.BoardDTO;
import com.project.dto.category.CategoryDTO;
import com.project.dto.mb.InsertUserRoleDTO;
import com.project.exception.NotFoundException;
import com.project.exception.UnknownException;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;

	private static final Logger logger = LogManager.getLogger(CategoryService.class);

	// 카테고리 메인화면
	public List<CategoryDTO> viewMainCategory() {
		String guestIP = IPConfig.getIp(SessionConfig.getSession());
		String checkId = SessionConfig.MbSessionDTO().getId();
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		List<CategoryDTO> viewMainCategory = Collections.emptyList();
		if (checkId == null || checkRole == null) {
			logger.warn("controllerCategory  access IP : {} have not right user", guestIP);
			throw new IllegalArgumentException("CategoryService controllerCategory have not right user");
		}

		if (checkAdmin.equals(checkRole)) {
			viewMainCategory = categoryDAO.viewMainCategory();
		} else {
			logger.error("controllerCategory  access ID : {} unknown status", checkId);
			throw new UnknownException("CategoryService controllerCategory에서 에상치 못한 상태가 발생했습니다..");
		}
		return viewMainCategory;
	}

	// 카테고리 추가
	public String createCategory(CategoryDTO categoryDTO) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean canCreateCategory = hasAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer createCheckCount;
		if (canCreateCategory) {
			categoryDTO = handleCreateCategoryWithManager(categoryDTO, checkId);
			createCheckCount = categoryDAO.createCategory(categoryDTO);
			resultMesg = checkResult(createCheckCount, checkId);
			resultMesg = categoryDTO.getCat() + " 카테고리가 추가 " + resultMesg;
		}
		return resultMesg;
	}

	// 투표로인한 카테고리 추가
	public String createCategoryByVote(BoardDTO boardDTO, Integer creationPoint) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		if (ConstantConfig.CREAT_CAT_POINT <=creationPoint) {
			logger.warn("access User : {} have not rihgt.", checkId);
			throw new IllegalArgumentException("You did not enough points.");
		}
		boolean canCreateCategory = hasAccessRightByCreatCat(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer createCheckCount;
		if (canCreateCategory) {
			CategoryDTO categoryDTO = handleCreateCategoryWithMember(boardDTO);
			createCheckCount = categoryDAO.createCategoryByVote(categoryDTO);
			handleInsertRole(boardDTO, checkId);
			resultMesg = checkResult(createCheckCount, checkId);
		}
		return resultMesg;
	}

	// 카테고리 관리자 수정
	public String updateMng(String catDomain, String id) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean canUpdaeManager = hasAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer updateCheckCount;
		if (canUpdaeManager) {
			CategoryDTO categoryDTO = handleUpdateMng(catDomain, id, checkId);
			updateCheckCount = categoryDAO.updateMng(categoryDTO);
			resultMesg = id + "매니저로 변경 " + checkResult(updateCheckCount, checkId);
		}
		return resultMesg;
	}

	// 카테고리 이름 변경
	public String updateCat(String catDomain, String cat) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean canUpdateCategory = hasAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer updateCheckCount;
		if (canUpdateCategory) {
			CategoryDTO categoryDTO = handleUpdateCat(catDomain, cat, checkId);
			updateCheckCount = categoryDAO.updateCat(categoryDTO);
			resultMesg = cat + "으로 변경 " + checkResult(updateCheckCount, checkId);
		}
		return resultMesg;
	}

	// 카테고리 삭제
	// 카테고리 로그 테이블 따로 존재 트리거 작동
	public String deleteCat(String catDomain) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean canDeleteCategory = hasAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer insertCheckCount;
		if (canDeleteCategory) {
			insertCheckCount = categoryDAO.deleteCat(catDomain);
			resultMesg = checkResult(insertCheckCount, checkId);
			resultMesg = catDomain + " 삭제 " + resultMesg;
		}
		return resultMesg;
	}

	// handleCreateCategory 핸들링
	private CategoryDTO handleCreateCategoryWithManager(CategoryDTO categoryDTO, String checkId) {
		categoryDTO.setCrtNm(checkId);
		categoryDTO.setMng(checkId);
		categoryDTO.setBoardCnt(ConstantConfig.INSERTSTARTNUM);
		categoryDTO.setRplCnt(ConstantConfig.INSERTSTARTNUM);
		return categoryDTO;
	}

	// handleInsertCategoryByMb 핸들링.
	private CategoryDTO handleCreateCategoryWithMember(BoardDTO boardDTO) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setMng(boardDTO.getCreator());
		categoryDTO.setCatDomain(boardDTO.getTtl());
		categoryDTO.setCat(boardDTO.getCn());
		categoryDTO.setCrtNm(boardDTO.getCreator());
		categoryDTO.setBoardCnt(ConstantConfig.INSERTSTARTNUM);
		categoryDTO.setRplCnt(ConstantConfig.INSERTSTARTNUM);
		return categoryDTO;
	}

	// 권한 부여 핸들링
	private void handleInsertRole(BoardDTO boardDTO, String checkId) {
		InsertUserRoleDTO insertUserRoleDTO = new InsertUserRoleDTO();
		insertUserRoleDTO.setCatDomain(boardDTO.getCatDomain());
		insertUserRoleDTO.setId(boardDTO.getCreator());
		insertUserRoleDTO.setRoleNum(UserRole.MNG.getLevel());
		Integer insertCheckCount = categoryDAO.insertMngRole(insertUserRoleDTO);
		String resultMesg = checkResult(insertCheckCount, checkId);
		if (ConstantConfig.FALSE_MESG.equals(resultMesg)) {
			logger.warn("access User : {} DB is not affected.", checkId);
			throw new IllegalArgumentException("insert false, try it.");
		}
	}

	// updateMng 핸들링.
	private CategoryDTO handleUpdateMng(String catDomain, String id, String checkId) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCatDomain(catDomain);
		categoryDTO.setUpNm(checkId);
		categoryDTO.setMng(id);
		return categoryDTO;
	}

	// UpdateCat 핸들링
	private CategoryDTO handleUpdateCat(String catDomain, String cat, String checkId) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCatDomain(catDomain);
		categoryDTO.setCat(cat);
		categoryDTO.setUpNm(checkId);
		return categoryDTO;
	}

	// 접근 권한 확인.
	private boolean hasAccessRight(String checkId) {
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		boolean resultMesg = false;
		if (checkId == null) {
			logger.error("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		} else if (!checkAdmin.equals(checkRole)) {
			logger.error("access User : {} have not rihgt.", checkId);
			throw new IllegalArgumentException("비정상적인 접근입니다.");
		} else if (checkAdmin.equals(checkRole)) {
			resultMesg = true;
		}

		return resultMesg;
	}

	// 생성 권한 확인.
	private boolean hasAccessRightByCreatCat(String checkId) {
		Integer checkRole = SessionConfig.MbSessionDTO().getRoleList().get(0).getRoleNum();
		Integer checkRoleLevel = UserRole.BASIC.getLevel();
		boolean resultMesg = false;
		if (checkId == null) {
			logger.error("세션에서 정보를 찾을 수 없는 상황에 접급 했습니다.");
			throw new UnknownException("세션정보가 없는 상황에서 접근");
		} else if (checkRoleLevel > checkRole) {
			logger.error("access User : {} have not rihgt.", checkId);
			throw new IllegalArgumentException("비정상적인 접근입니다.");
		} else if (checkRole >= checkRoleLevel) {
			resultMesg = true;
		}
		return resultMesg;
	}

	// DB 결과 확인
	private String checkResult(Integer insertCheck, String user) {
		String resultMesg = null;
		if (insertCheck == ConstantConfig.SUCCESS_COUNT) {
			resultMesg = ConstantConfig.SUCCESS_MESG;
		} else if (insertCheck == ConstantConfig.FALSE_COUNT) {
			logger.warn("access User : {} DB is not affected.", user);
			throw new NotFoundException("결과 값이 정확하지 않습니다.");
		} else {
			logger.error("access User : {} unknown status", user);
			throw new UnknownException("CategoryService에서 예상치 못한 상태가 발생했습니다.");
		}
		return resultMesg;
	}

}
