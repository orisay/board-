package com.project.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.ConstantUserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.CategoryDAO;
import com.project.dto.CategoryDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;

	private static final Logger logger = LogManager.getLogger(CategoryService.class);

	// 카테고리 메인화면
	public List<CategoryDTO> controllerCategory() {
		String guestIP = IPConfig.getIp(SessionConfig.getSession());
		String checkId = SessionConfig.MbSessionDTO().getId();
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		List<CategoryDTO> controllerCategory = Collections.emptyList();

		if (checkId == null || checkRole == null) {
			logger.warn("controllerCategory  access IP : {} have not right user", guestIP);
			throw new IllegalArgumentException("CategoryService controllerCategory have not right user");
		}

		if (checkAdmin.equals(checkRole)) {
			controllerCategory = categoryDAO.controllerCategory();
		} else {
			logger.error("controllerCategory  access ID : {} unknown status", checkId);
			throw new UnknownException("CategoryService controllerCategory에서 에상치 못한 상태가 발생했습니다..");
		}
		return controllerCategory;
	}

	// 카테고리 추가
	public String insertCategory(CategoryDTO categoryDTO) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean rightCheck = getAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer insertCount;
		if (rightCheck) {
			categoryDTO.setCrtNm(checkId);
			categoryDTO.setMng(checkId);
			categoryDTO.setBoardCnt(ConstantConfig.insertStartNum);
			categoryDTO.setRplCnt(ConstantConfig.insertStartNum);
			insertCount = categoryDAO.insertCategory(categoryDTO);
			resultMesg = checkResult(insertCount, checkId);
			resultMesg = categoryDTO.getCat() + " 카테고리가 추가 " + resultMesg;
		}
		return resultMesg;
	}

	// 카테고리 관리자 수정
	public String updateMng(String catDomain, String id) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean rightCheck = getAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer insertCount;
		if (rightCheck) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setUpNm(checkId);
			categoryDTO.setMng(id);
			insertCount = categoryDAO.updateMng(categoryDTO);
			resultMesg = id + "매니저로 변경 " + checkResult(insertCount, checkId);
		}
		return resultMesg;
	}

	// 카테고리 이름 변경
	public String updateCat(String catDomain, String cat) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean rightCheck = getAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer insertCount;
		if (rightCheck) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setCat(cat);
			categoryDTO.setUpNm(checkId);
			insertCount = categoryDAO.updateCat(categoryDTO);
			resultMesg = cat + "으로 변경 " + checkResult(insertCount, checkId);
		}
		return resultMesg;
	}

	// 카테고리 삭제
	// 카테고리 로그 테이블 따로 존재 트리거 작동
	public String deleteCat(String catDomain) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		boolean rightCheck = getAccessRight(checkId);
		String resultMesg = ConstantConfig.FALSE_MESG;
		Integer insertCount;
		if (rightCheck) {
			insertCount = categoryDAO.deleteCat(catDomain);
			resultMesg = checkResult(insertCount, checkId);
			resultMesg = catDomain + " 삭제 " + resultMesg;
		}
		return resultMesg;
	}

	//접근 권한 확인.
	private boolean getAccessRight(String checkId) {
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

	// DB 결과 확인
	private String checkResult(Integer insertCheck, String user) {
		String resultMesg = null;
		if (insertCheck == ConstantConfig.SUCCESS_COUNT) {
			resultMesg = ConstantConfig.SUCCESS_MESG;
		} else if (insertCheck == ConstantConfig.FALSE_COUNT) {
			logger.warn("access User : {} DB is not affected.", user);
			resultMesg = ConstantConfig.FALSE_MESG;
		} else {
			logger.error("access User : {} unknown status", user);
			throw new UnknownException("CategoryService에서 예상치 못한 상태가 발생했습니다.");
		}
		return resultMesg;
	}

}
