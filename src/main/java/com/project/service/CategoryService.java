package com.project.service;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.SessionConfig;
import com.project.dao.CategoryDAO;
import com.project.dto.CategoryDTO;
import com.project.exception.UnknownException;

@Service
public class CategoryService {

	@Autowired
	CategoryDAO categoryDAO;

	private static final Logger logger = LogManager.getLogger(CategoryService.class);

	// 카테고리 메인화면
	@Transactional
	public List<CategoryDTO> controllerCategory() {
		String checkId = SessionConfig.getMbDTO().getId();
		String checkRole = SessionConfig.getMbDTO().getRole();
		List<CategoryDTO> controllerCategory = Collections.emptyList();

		if (checkRole != null && "admin".equals(checkRole)) {
			controllerCategory = categoryDAO.controllerCategory(checkRole);
		} else if (!"admin".equals(checkRole)) {
			logger.warn("controllerCategory  access ID : {} have not right user", checkId);
		} else {
			logger.fatal("controllerCategory  access ID : {} unkonwn status", checkId);
			throw new UnknownException("CategoryService controllerCategory에서 에상치 못한 상태가 발생했습니다..");
		}
		return controllerCategory;
	}

	// 카테고리 추가
	// 여기서는 싱크로 나이즈 필요하지 않을까?
	@Transactional
	public String insertCategory(CategoryDTO categoryDTO) {
		String checkId = SessionConfig.getMbDTO().getId();
		String checkRole = SessionConfig.getMbDTO().getRole();
		String mesg = "";
		if (checkRole != null &&"admin".equals(checkRole)) {
			categoryDTO.setCrtNm(checkId);
			categoryDTO.setMng(checkId);
			categoryDAO.insertCategory(categoryDTO);
			mesg = categoryDTO.getCat() + "카테고리가 추가 되었습니다.";
		} else {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("insertCategory access ID : {} have not right user", checkId);
		}
		return mesg;
	}

	// 카테고리 관리자 수정
	@Transactional
	public String updateMng(String catDomain, String id) {
		String checkId = SessionConfig.getMbDTO().getId();
		String checkRole = SessionConfig.getMbDTO().getRole();
		String mesg = "";
		if (catDomain != null && id != null && "admin".equals(checkRole)) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setUpNm(checkId);
			categoryDTO.setMng(id);
			categoryDAO.updateMng(categoryDTO);
			mesg = id + " 님이 관리자가 되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("updateMng access ID : {} have not right user", checkId);
		} else if (catDomain == null || id == null) {
			logger.warn("updateMng access ID : {} insert null value catDomain : {}, id : {}"
					, checkId, catDomain, id);
		} else {
			logger.fatal("updateMng access ID : {} unkonwn status", checkId);
			throw new UnknownException("CategoryService updateMng에서 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

	// 카테고리 이름 변경
	@Transactional
	public String updateCat(String catDomain, String cat) {
		String checkId = SessionConfig.getMbDTO().getId();
		String checkRole = SessionConfig.getMbDTO().getRole();
		String mesg = "";
		if (catDomain != null && cat != null && "admin".equals(checkRole)) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCatDomain(catDomain);
			categoryDTO.setCat(cat);
			categoryDTO.setUpNm(checkId);
			categoryDAO.updateCat(categoryDTO);
			mesg = cat + "으로 변경되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("updateCat access ID : {} have not right user", checkId);
		} else if (catDomain == null || cat == null) {
			logger.warn("updateCat access ID : {} insert null value catDomain : {}, cat : {}"
					, checkId, catDomain, cat);
		} else {
			logger.fatal("updateCat access ID : {} unkonwn status", checkId);
			throw new UnknownException("CategoryService updateCat 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

	// 카테고리 삭제
	// 카테고리 로그 테이블 따로 존재 트리거 작동
	@Transactional
	public String deleteCat(String catDomain) {
		String checkId = SessionConfig.getMbDTO().getId();
		String checkRole = SessionConfig.getMbDTO().getRole();
		String mesg = "";
		if (catDomain != null &&"admin".equals(checkRole)) {
			categoryDAO.deleteCat(catDomain);
			mesg = catDomain + "삭제 되었습니다.";
		} else if (!"admin".equals(checkRole)) {
			mesg = "권한 없는 사용자 입니다.";
			logger.warn("Method : updateCat access ID : {} have not right user", checkId);
		} else if (catDomain == null) {
			logger.warn("Method : updateCat access ID : {} insert  null value catDomain : {}"
					, checkId, catDomain);
			mesg = "catDomain을 알 수 없습니다.";
		} else {
			logger.fatal("updateCat access ID : {} unkonwn status", checkId);
			throw new UnknownException("CategoryService deleteCat 예상치 못한 상태가 발생했습니다.");
		}
		return mesg;
	}

}
