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

	@Transactional
	public String getId(String id) {
		Integer checkId = mbDAO.getId(id);
		String checkIdMesg = "";
		if (checkId == 1) {
			checkIdMesg = "이미 사용한 ID 입니다.";
		} else if (checkId == 0) {
			checkIdMesg = "사용 가능한 ID 입니다.";
		} else {
			logger.info("MbService getId fail insertValue : " + id + "resultValue : " + checkId);
		}
		return checkIdMesg;
	}

	@Transactional
	public String getLogin(MbDTO mbDTO) {
		Integer checkLogin = mbDAO.getLogin(mbDTO);
		String checkLoginMesg = "";
		if (checkLogin == 1) {
			checkLoginMesg = "로그인 성공";
		} else if (checkLogin == 0) {
			checkLoginMesg = "로그인 실패";
		} else {
			logger.info("MbService getLogin fail insertValue : " + mbDTO + " id : " + mbDTO.getId()
			+ "pw : " + mbDTO.getPw());
		}
		return checkLoginMesg;
	}

	public void getLogout() {
		SessionConfig.getSession().invalidate();
	}

	@Transactional
	public MbDTO getMyPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public String updateMyPage(MbDTO mbDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
