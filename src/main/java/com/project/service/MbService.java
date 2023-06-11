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
import com.project.dto.SearchInfoDTO;
import com.project.dto.UpdatePwDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class MbService {

	private static final Logger logger = LogManager.getLogger(MbService.class);

	@Autowired
	MbDAO mbDAO;

	// 회원 가입
	public String insertMb(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String checkId = null;
		Integer insertCheck = null;
		String mesg = null;
		if (mbDTO == null) {
			logger.warn("insertMb  access IP : {} insert vaule null", gusetIP);
			throw new IllegalArgumentException("MbService insertMb insert null value");
		} else {
			String insertId = mbDTO.getId();
			checkId = insertId.trim();
		}
		if (!ArrayUtils.contains(ConstantConfig.nullList, checkId)) {
			mbDTO.setId(checkId);
			insertCheck = mbDAO.insertMb(mbDTO);
		}

		if (insertCheck == 1) {
			mbDAO.insertRole(mbDTO);
			mesg = "회원 가입 성공 ID : " + checkId;
		} else if (insertCheck == 0) {
			mesg = "회원 가입 실패";
		} else {
			logger.error("insertMb access IP : {} unknown status", gusetIP);
			throw new UnknownException("MbService insertMb에서 비정상적인 값이 발생 했습니다.");
		}
		return mesg;
	}

	// 아이디 중복 검사
	public String checkId(String id) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String insertId = null;
		String checkIdMesg = null;

		if (id == null) {
			logger.warn("checkId access ID : {} null value", gusetIP);
			throw new IllegalArgumentException("MbService checkId insert null value");
		}

		insertId = mbDAO.getId(id);
		if (insertId != null && !insertId.isEmpty()) {
			checkIdMesg = "이미 사용한 ID 입니다." + id;
		} else if (insertId == null) {
			checkIdMesg = "사용 가능한 ID 입니다." + id;
		} else if (insertId.isEmpty()) {
			logger.error("checkId access IP : {} return empty id", gusetIP);
			throw new IllegalStateException("MbService checkId에서 비정상적인 값이 발생 했습니다.");
		} else {
			logger.error("checkId access IP : {} unknown status", gusetIP);
			throw new UnknownException("MbService checkId 예기치 못한 상태가 발생했습니다.");
		}
		return checkIdMesg;
	}

	// 로그인
	public String getLogin(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		MbDTO resultLogin = null;
		String checkLoginMesg = null;

		if (mbDTO == null) {
			logger.warn("checkId access ID : {} null value", gusetIP);
			throw new IllegalArgumentException("MbService getLogin insert null value");
		}
		resultLogin = mbDAO.getLogin(mbDTO);
		if (mbDTO.getId().equals(resultLogin.getId())) {
			checkLoginMesg = "로그인 성공." + mbDTO.getId();
		} else if (!mbDTO.getId().equals(resultLogin.getId())) {
			logger.error("getLogin access IP : {} unkonwn status insert value : {}, Id : {}, Pw : {}"
					, gusetIP, mbDTO, mbDTO.getId(), mbDTO.getPw());
			throw new UnknownException("MbService getLogin에서 비정상적인 값이 발생 했습니다.");
		} else {
			checkLoginMesg = "로그인 실패.";
		}
		return checkLoginMesg;
	}

	// 로그아웃
	public void getLogout() {
		SessionConfig.getSession().invalidate();
	}

	// 마이 페이지
	public MbDTO getMyPage() {
		String mbId = SessionConfig.MbSessionDTO().getId();
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
	public String updateMyPage(MbDTO mbDTO) {
		String memberId = SessionConfig.MbSessionDTO().getId();
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
			logger.error("updateMyPage access ID : {} unknown status", memberId);
			throw new UnknownException("MbService updateMyPage에서 비정상적인 값이 발생 했습니다.");
		}
		return updateMyPageMesg;
	}

	// 비밀번호 변경
	public String updatePw(UpdatePwDTO updatePwDTO) {
		String memberId = SessionConfig.MbSessionDTO().getId();
		Integer insertCheck = null;
		if (updatePwDTO == null) {
			logger.warn("updatePw access ID : {} null vaule updatePwDTO : {}", memberId, updatePwDTO);
			throw new IllegalArgumentException(
					"MbService updateMyPage null value updatePwDTO : " + updatePwDTO);
		} else {
			updatePwDTO.setId(memberId);
			insertCheck = mbDAO.updatePw(updatePwDTO);
		}

		String sueccesMesg = null;
		if (insertCheck == 1) {
			sueccesMesg = "변경 성공했습니다.";
		} else if (insertCheck == 0) {
			sueccesMesg = "변경 실패했습니다.";
			logger.warn("updatePw checkId access ID : {} ,DB is not affected", memberId);
		} else {
			logger.error("updatePw access ID : {} unknown status", memberId);
			throw new UnknownException("updatePw 예기치 못한 상황이 발생했습니다.");
		}
		return sueccesMesg;
	}

	// 아이디 찾기
	public String searchId(SearchInfoDTO searchInfoDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String serachIdCheck = null;

		if (searchInfoDTO == null) {
			logger.warn("access IP : {} null vaule searchInfoDTO : {}", gusetIP, searchInfoDTO);
			throw new IllegalArgumentException(
					"MbService searchId null value searchInfoDTO : " + searchInfoDTO );
		} else {
			serachIdCheck = mbDAO.searchId(searchInfoDTO);
		}

		String sueccesMesg = null;
		if (serachIdCheck != null && !serachIdCheck.isEmpty()) {
			sueccesMesg = "성공했습니다.." + serachIdCheck;
		} else if (serachIdCheck.isEmpty()) {
			sueccesMesg = "실패했습니다..";
			logger.warn("checkId access IP : {} ,DB is not affected", gusetIP);
		} else {
			logger.error("checkId access IP : {} unknown status", gusetIP);
			throw new UnknownException("updatePw 예기치 못한 상황이 발생했습니다.");
		}
		return sueccesMesg;
	}

	// 비밀번호 찾기
	public String searchPw(SearchInfoDTO searchInfoDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String searchPwCheck = null;
		if (searchInfoDTO == null) {
			logger.warn("access IP : {} null vaule searchInfoDTO : {}", gusetIP, searchInfoDTO);
			StringBuilder errorMesg = new StringBuilder();
			errorMesg.append("MbService searchId null value");
			errorMesg.append("searchInfoDTO : ");
			errorMesg.append(searchInfoDTO);
			throw new IllegalArgumentException(errorMesg.toString());
		} else {
			searchPwCheck = mbDAO.searchPw(searchInfoDTO);
		}

		String sueccesMesg = null;
		if (searchPwCheck != null && !searchPwCheck.isEmpty()) {
			sueccesMesg = "성공했습니다." + searchPwCheck;
		} else if (searchPwCheck.isEmpty()) {
			sueccesMesg = "실패했습니다.";
			logger.warn("checkId access IP : {} ,DB is not affected", gusetIP);
		} else {
			logger.error("searchPw access IP : {} unknown status", gusetIP);
			throw new UnknownException("searchPw에서 예기치 못한 상황이 발생했습니다.");
		}
		return sueccesMesg;

	}

}
