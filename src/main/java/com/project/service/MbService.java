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

@Service
public class MbService {

	private static final Logger logger = LogManager.getLogger(MbService.class);

	@Autowired
	MbDAO mbDAO;

	// 회원 가입
	@Transactional
	public String insertMb(MbDTO mbDTO) {
		String nullCheck = mbDTO.getId();
		String mbId = "";
		String mesg = "";
		if (!ArrayUtils.contains(ConstantConfig.nullList, nullCheck)) {
			mbId = mbDAO.insertMb(mbDTO);
		}
		if (mbId != null && !mbId.isEmpty()) {
			mesg = "회원 가입 성공 ID : " + mbId;
		} else if (mbId == null) {
			mesg = "회원 가입 실패";
		} else {
			logger.info("MbService insertMb fail insertValue : " + mbDTO + "resultValue : " + mbId + "IP addr : "
					+ IPConfig.getIp(SessionConfig.getSession()));
		}
		return mesg;
	}

	//아이디 중복 검사
	@Transactional
	public String checkId(String id) {
		Integer insertId = mbDAO.getId(id);
		String checkIdMesg = "";
		if (insertId == 1) {
			checkIdMesg = "이미 사용한 ID 입니다." + id;
		} else if (insertId == 0) {
			checkIdMesg = "사용 가능한 ID 입니다." + id;
		} else {
			logger.info("MbService getId fail insertValue : " + id + "resultValue : " + insertId);
		}
		return checkIdMesg;
	}

	//로그인
	@Transactional
	public String getLogin(MbDTO mbDTO) {
		Integer resultLogin = mbDAO.getLogin(mbDTO);
		String checkLoginMesg = "";
		if (resultLogin == 1) {
			checkLoginMesg = "로그인 성공." + mbDTO.getId();
		} else if (resultLogin == 0) {
			checkLoginMesg = "로그인 실패.";
		} else {
			logger.info("MbService getLogin fail insertValue : " + mbDTO + " id : " + mbDTO.getId() + "pw : "
					+ mbDTO.getPw());
		}
		return checkLoginMesg;
	}

	//로그아웃
	public void getLogout() {
		SessionConfig.getSession().invalidate();
	}

	//마이 페이지
	@Transactional
	public MbDTO getMyPage() {
		String mbId = SessionConfig.getMbDTO().getId();
		MbDTO mbDTO = new MbDTO();
		if (mbId != null) {
			mbDTO = mbDAO.getMyPage(mbId);
		} else {
			logger.info("MbService getMyPage faile mbId vaule null");
		}
		return mbDTO;
	}

	//마이 페이지 수정
	@Transactional
	public String updateMyPage(MbDTO mbDTO) {
		Integer updateMyPage = mbDAO.updateMyPage(mbDTO);
		String updateMyPageMesg = "";
		if (updateMyPage == 1) {
			updateMyPageMesg = "마이 페이지 수정 성공";
		} else if (updateMyPage == 0) {
			updateMyPageMesg = "마이 페이지 수정 실패";
		} else {
			logger.info("MbService updateMyPage fail insert value :" + mbDTO + "resultValue :" + updateMyPage);
		}
		return updateMyPageMesg;
	}

}
