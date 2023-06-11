package com.project.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.ConstantConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.CategoryDAO;
import com.project.dto.CategoryDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class CategoryService {

	@Autowired
	CategoryDAO categoryDAO;

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
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		String mesg = null;

		if (categoryDTO == null) {
			logger.warn("insertCategory access ID : {} insert null value : {}", checkId, categoryDTO);
			throw new IllegalArgumentException(
					"CategoryService insertCategory insert null value categoryDTO : " + categoryDTO);
		}

		if (checkRole != null && checkAdmin.equals(checkRole)) {
			categoryDTO.setCrtNm(checkId);
			categoryDTO.setMng(checkId);
			categoryDTO.setBoardCnt(ConstantConfig.insertStartNum);
			categoryDTO.setRplCnt(ConstantConfig.insertStartNum);
			categoryDAO.insertCategory(categoryDTO);
			mesg = categoryDTO.getCat() + "카테고리가 추가 되었습니다.";
		} else {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("insertCategory access ID : {} have not right user", checkId);
		}
		return mesg;
	}

	// 카테고리 관리자 수정
	public String updateMng(String catDomain, String id) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		String mesg = null;

		if (catDomain == null || id == null) {
			logger.warn("updateMng access ID : {} insert null value catDomain : {}, id : {}"
					, checkId, catDomain, id);
			throw new IllegalArgumentException(
					"CategoryService updateMng insert null value catDomain : " + catDomain + " id : " + id);
		}

		if (catDomain != null && id != null && checkAdmin.equals(checkRole)) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setUpNm(checkId);
			categoryDTO.setMng(id);
			categoryDAO.updateMng(categoryDTO);
			mesg = id + " 님이 관리자가 되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("updateMng access ID : {} have not right user", checkId);
		} else {
			logger.error("updateMng access ID : {} unknown status", checkId);
			throw new UnknownException("CategoryService updateMng에서 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

	// 카테고리 이름 변경
	public String updateCat(String catDomain, String cat) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		String mesg = null;

		if (catDomain == null || cat == null) {
			logger.warn("updateCat access ID : {} insert null value catDomain : {}, cat : {}"
					, checkId, catDomain, cat);
			throw new IllegalArgumentException(
					"CategoryService updateCat insert null value catDomain : " + catDomain + " cat : " + cat);
		}

		if (catDomain != null && cat != null && checkAdmin.equals(checkRole)) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setCat(cat);
			categoryDTO.setUpNm(checkId);
			categoryDAO.updateCat(categoryDTO);
			mesg = cat + "으로 변경되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("updateCat access ID : {} have not right user", checkId);
		} else {
			logger.error("updateCat access ID : {} unknown status", checkId);
			throw new UnknownException("CategoryService updateCat 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

	// 카테고리 삭제
	// 카테고리 로그 테이블 따로 존재 트리거 작동
	public String deleteCat(String catDomain) {
		String checkId = SessionConfig.MbSessionDTO().getId();
		String checkRole = SessionConfig.MbSessionDTO().getRole();
		String checkAdmin = UserRole.ADMIN.name();
		String mesg = null;

		if (catDomain == null) {
			logger.warn("updateCat access ID : {} insert  null value catDomain : {}"
					, checkId, catDomain);
			throw new IllegalArgumentException(
					"CategoryService deleteCat " + "insert null value catDomain : " + catDomain);
		}
		if (catDomain != null && checkAdmin.equals(checkRole)) {
			categoryDAO.deleteCat(catDomain);
			mesg = catDomain + "삭제 되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("updateCat access ID : {} have not right user", checkId);
		} else {
			logger.error("updateCat access ID : {} unknown status", checkId);
			throw new UnknownException("CategoryService deleteCat 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

}
