package com.project.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.MbDAO;
import com.project.dto.MbDTO;
import com.project.exception.UnknownException;

@Service
public class MbService {

	private static final Logger logger = LogManager.getLogger(MbService.class);

	@Autowired
	MbDAO mbDAO;

	// 회원 가입
	@Transactional
	public String insertMb(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String nullCheck = "";
		String mbId = "";
		String mesg = "";

		if (mbDTO != null) {
			nullCheck = mbDTO.getId();
		} else {
			logger.warn("insertMb  access IP : {} insert vaule null", gusetIP);
			throw new IllegalArgumentException();
		}

		if (!ArrayUtils.contains(ConstantConfig.nullList, nullCheck)) {
			mbId = mbDAO.insertMb(mbDTO);
		}

		if (mbId != null && !mbId.isEmpty()) {
			mesg = "회원 가입 성공 ID : " + mbId;
		} else if (mbId == null || mbId.isEmpty()) {
			mesg = "회원 가입 실패";
		} else {
			logger.fatal("insertMb access IP : {} unkonwn status", gusetIP);
			throw new UnknownException("MbService insertMb에서 비정상적인 값이 발생 했습니다.");
		}
		return mesg;
	}

	// 아이디 중복 검사
	@Transactional
	public String checkId(String id) {
		Integer insertId = 0;
		String checkIdMesg = "";
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());

		if (id != null) {
			insertId = mbDAO.getId(id);
		} else {
			logger.warn("checkId access ID : {} null value id", gusetIP);
		}

		if (insertId == 1) {
			checkIdMesg = "이미 사용한 ID 입니다." + id;
		} else if (insertId == 0) {
			checkIdMesg = "사용 가능한 ID 입니다." + id;
		} else {
			logger.fatal("checkId access IP : {} unkonwn status", gusetIP);
			throw new UnknownException("MbService checkId에서 비정상적인 값이 발생 했습니다.");
		}
		return checkIdMesg;
	}

	// 로그인
	@Transactional
	public String getLogin(MbDTO mbDTO) {
		Integer resultLogin = mbDAO.getLogin(mbDTO);
		String checkLoginMesg = "";
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());

		if (resultLogin == 1) {
			checkLoginMesg = "로그인 성공." + mbDTO.getId();
		} else if (resultLogin == 0) {
			checkLoginMesg = "로그인 실패.";
		} else {
			logger.fatal("getLogin access IP : {} unkonwn status insert value : {}, Id : {}, Pw : {}"
					, gusetIP, mbDTO, mbDTO.getId(), mbDTO.getPw());
			throw new UnknownException("MbService getLogin에서 비정상적인 값이 발생 했습니다.");
		}
		return checkLoginMesg;
	}

	// 로그아웃
	public void getLogout() {
		SessionConfig.getSession().invalidate();
	}

	// 마이 페이지
	@Transactional
	public MbDTO getMyPage() {
		String mbId = SessionConfig.getMbDTO().getId();
		MbDTO mbDTO = new MbDTO();
		if (mbId != null) {
			mbDTO = mbDAO.getMyPage(mbId);
		} else {
			logger.warn("getMyPage access ID : {} null value", mbId);
		}
		return mbDTO;
	}

	// 마이 페이지 수정
	@Transactional
	public String updateMyPage(MbDTO mbDTO) {
		String memberId = SessionConfig.getMbDTO().getId();
		Integer updateMyPage = 0;

		if (mbDTO != null) {
			updateMyPage = mbDAO.updateMyPage(mbDTO);
		} else {
			logger.warn("updateMyPage access ID : {} null vaule");
		}
		String updateMyPageMesg = "";
		if (updateMyPage == 1) {
			updateMyPageMesg = "마이 페이지 수정 성공";
		} else if (updateMyPage == 0) {
			updateMyPageMesg = "마이 페이지 수정 실패";
		} else {
			logger.fatal("updateMyPage access ID : {} unkonwn status", memberId);
			throw new UnknownException("MbService updateMyPage에서 비정상적인 값이 발생 했습니다.");
		}
		return updateMyPageMesg;
	}

}
