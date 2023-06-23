package com.project.service;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.config.ConstantConfig;
import com.project.config.ConstantUserRoleConfig;
import com.project.config.ConstantUserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.MbDAO;
import com.project.dto.MbDTO;
import com.project.dto.MbRoleDTO;
import com.project.dto.MbSessionDTO;
import com.project.dto.SearchInfoDTO;
import com.project.dto.UpdatePwDTO;
import com.project.exception.UnknownException;

@Service
@Transactional
public class MbService {

	private static final Logger logger = LogManager.getLogger(MbService.class);

	@Autowired
	private MbDAO mbDAO;

	// 회원 가입
	public String insertMb(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String inputId = mbDTO.getId();
		String checkId = inputId.trim();
		String reusltMesg = null;

		Integer insertCheckCount = compareNullCheckId(mbDTO, checkId);
		String checkResult = checkResultByInteger(insertCheckCount, gusetIP);
		reusltMesg = checkId + "회원가입 " + checkResult;
		insertBasicRole(checkResult, checkId);
		return reusltMesg;
	}

	// 아이디 NULL 대소문자 조합 방지
	private Integer compareNullCheckId(MbDTO mbDTO, String checkId) {
		Integer insertCheckCount = null;
		if (!ArrayUtils.contains(ConstantConfig.nullList, checkId)) {
			insertCheckCount = mbDAO.insertMb(mbDTO);
		} else {
			logger.error("access IP : {} unknown status, retry checkId", checkId);
			throw new UnknownException("비정상적인 값 발생, retry checkId");
		}
		return insertCheckCount;
	}

	// BASIC 권한 부여
	private void insertBasicRole(String checkResult, String checkId) {
		if (checkResult.equals(ConstantConfig.SUCCESS_MESG)) {
			mbDAO.insertRole(checkId);
		} else if (checkResult.equals(ConstantConfig.FALSE_MESG)) {
			logger.error("access ID : {}, insertMb false", checkId);
		} else {
			logger.error("access ID : {}, unknown status", checkId);
			throw new UnknownException("Guard coad. Please check.");
		}

	}

	// 아이디 중복 검사
	public String compareId(String id) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String insertCheckMesg = mbDAO.compareId(id);
		String resultCheck = checkResultByString(gusetIP, insertCheckMesg);
		String resultMesg = id + "는(은) " + resultCheck;
		return resultMesg;
	}

	// 로그인
	public MbSessionDTO getLogin(MbDTO mbDTO) {
		MbDTO getLogin = mbDAO.getLogin(mbDTO);
		MbSessionDTO resultMesg = handelMbSessionDTO(getLogin);
		return resultMesg;
	}

	// 로그인 핸들링
	private MbSessionDTO handelMbSessionDTO(MbDTO getLogin) {
		String userIp = IPConfig.getIp(SessionConfig.getSession());
		MbSessionDTO setUser = null;
		if (getLogin != null) {
			setUser = new MbSessionDTO();
			List<MbRoleDTO> roleList = mbDAO.getRoleList(getLogin.getId());
			UserRole userRole = ConstantUserRoleConfig.UserRole.getRole(roleList.get(0).getRoleNum());
			setUser.setRole(userRole.name());
			setUser.setRoleList(roleList);
			setUser.setId(getLogin.getId());
			setUser.setPw(getLogin.getPw());
			setUser.setAls(getLogin.getAls());
			setUser.setNm(getLogin.getNm());
			logger.info("access ID : {}, Login", getLogin.getId());
		} else {
			logger.warn("access ID : {} ,DB is not affected", userIp);
			throw new IllegalStateException("DB is not affected check Please");
		}
		return setUser;
	}

	// 로그아웃
	public void getLogout() {
		SessionConfig.getSession().invalidate();
	}

	// 마이 페이지
	public MbDTO getMyPage() {
		String mbId = SessionConfig.MbSessionDTO().getId();
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		MbDTO mbDTO = mbDAO.getMyPage(mbId);
		checkResultByDTO(mbDTO, gusetIP);
		return mbDTO;
	}

	// 마이 페이지 수정
	public String updateMyPage(MbDTO mbDTO) {
		String memberId = SessionConfig.MbSessionDTO().getId();
		Integer insertCheckCount = mbDAO.updateMyPage(mbDTO);
		String checkResult = checkResultByInteger(insertCheckCount, memberId);
		String reusltMesg = "마이페이지 수정" + checkResult;
		return reusltMesg;
	}

	// 비밀번호 변경
	public String updatePw(UpdatePwDTO updatePwDTO) {
		String memberId = SessionConfig.MbSessionDTO().getId();
		Integer insertCheck = null;
		if (updatePwDTO == null) {
			logger.warn("updatePw access ID : {} null vaule updatePwDTO : {}", memberId, updatePwDTO);
			throw new IllegalArgumentException("MbService updateMyPage null value updatePwDTO : " + updatePwDTO);
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
			throw new IllegalArgumentException("MbService searchId null value searchInfoDTO : " + searchInfoDTO);
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

	// DB 출력값 확인
	private String checkResultByInteger(Integer insertCheckCount, String user) {
		String resultMesg = null;
		if (insertCheckCount == ConstantConfig.SUCCESS_COUNT) {
			resultMesg = ConstantConfig.SUCCESS_MESG;
		} else if (insertCheckCount == ConstantConfig.FALSE_COUNT) {
			resultMesg = ConstantConfig.FALSE_MESG;
		} else {
			logger.error("access IP : {}, unknown status", user);
			throw new UnknownException("DB is not affected. Please check.");
		}
		return resultMesg;
	}

	// DB 출력값 확인 (문자열)
	private String checkResultByString(String user, String insertId) {
		String resultMesg = null;
		if (!insertId.isEmpty()) {
			resultMesg = ConstantConfig.SUCCESS_MESG_BYString;
		} else if (!insertId.isEmpty()) {
			resultMesg = ConstantConfig.FALSE_MESG_BYString;
		} else {
			logger.error("access IP : {}, unknown status", user);
			throw new UnknownException("DB is not affected. Please check.");
		}
		return resultMesg;
	}

//	 DB 출력값 확인 (DTO)
	private void checkResultByDTO(MbDTO mbDTO, String gusetIP) {
		if (mbDTO != null) {
			logger.info("access ID : {}, Success", mbDTO.getId());
		} else {
			logger.error("access ID : {}, unknown status", gusetIP);
			throw new UnknownException("DB is not affected. Please check.");
		}
	}

}
