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
import com.project.config.ConstantConfig.UserRole;
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
		String nullCheck = null;
		String mbId = null;
		String mesg = null;
		if (mbDTO == null) {
			logger.warn("insertMb  access IP : {} insert vaule null", gusetIP);
			throw new IllegalArgumentException("MbService insertMb insert null value");
		} else {
			String nullCheckBasic = mbDTO.getId();
			nullCheck = nullCheckBasic.trim();
		}
		if (!ArrayUtils.contains(ConstantConfig.nullList, nullCheck)) {
			mbId = mbDAO.insertMb(mbDTO);
		}

		if (mbId != null && !mbId.isEmpty()) {
			mesg = "회원 가입 성공 ID : " + mbId;
		} else if (mbId == null || mbId.isEmpty()) {
			mesg = "회원 가입 실패";
		} else {
			logger.error("insertMb access IP : {} unknown status", gusetIP);
			throw new UnknownException("MbService insertMb에서 비정상적인 값이 발생 했습니다.");
		}
		return mesg;
	}

	// 아이디 중복 검사
	@Transactional
	public String checkId(String id) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		Integer insertId = null;
		String checkIdMesg = null;

		if (id == null) {
			logger.warn("checkId access ID : {} null value", gusetIP);
			throw new IllegalArgumentException("MbService checkId insert null value");
		}

		insertId = mbDAO.getId(id);

		if (insertId == 1) {
			checkIdMesg = "이미 사용한 ID 입니다." + id;
		} else if (insertId == 0) {
			checkIdMesg = "사용 가능한 ID 입니다." + id;
		} else {
			logger.error("checkId access IP : {} unknown status", gusetIP);
			throw new UnknownException("MbService checkId에서 비정상적인 값이 발생 했습니다.");
		}
		return checkIdMesg;
	}

	// 로그인
	@Transactional
	public String getLogin(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		Integer resultLogin = null;
		String checkLoginMesg = null;

		if (mbDTO == null) {
			logger.warn("checkId access ID : {} null value", gusetIP);
			throw new IllegalArgumentException("MbService getLogin insert null value");
		}

		resultLogin = mbDAO.getLogin(mbDTO);

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
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		MbDTO mbDTO = new MbDTO();

		if (mbId == null) {
			logger.warn("getMyPage access ID : {} have not session IP : {}", mbId, gusetIP);
			throw new IllegalArgumentException("MbService getMyPage hava not session");
		}
		mbDTO = mbDAO.getMyPage(mbId);
		return mbDTO;
	}

	// 마이 페이지 수정
	@Transactional
	public String updateMyPage(MbDTO mbDTO) {
		String memberId = SessionConfig.getMbDTO().getId();
		Integer updateMyPage = null;
		String updateMyPageMesg = null;

		if (mbDTO == null) {
			logger.warn("updateMyPage access ID : {} null vaule", memberId, mbDTO);
			throw new IllegalArgumentException("MbService updateMyPage null value" + mbDTO);
		}

		updateMyPage = mbDAO.updateMyPage(mbDTO);

		if (updateMyPage == 1) {
			updateMyPageMesg = "마이 페이지 수정 성공";
		} else if (updateMyPage == 0) {
			updateMyPageMesg = "마이 페이지 수정 실패";
		} else {
			logger.fatal("updateMyPage access ID : {} unknown status", memberId);
			throw new UnknownException("MbService updateMyPage에서 비정상적인 값이 발생 했습니다.");
		}
		return updateMyPageMesg;
	}

}
