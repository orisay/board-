package com.project.service;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.common.constant.ConstantConfig;
import com.project.common.constant.UserRoleConfig;
import com.project.common.constant.UserRoleConfig.UserRole;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.dao.MbDAO;
import com.project.dto.mb.MbDTO;
import com.project.dto.mb.MbRoleDTO;
import com.project.dto.mb.MbSessionDTO;
import com.project.dto.mb.SearchInfoDTO;
import com.project.dto.mb.UpdatePwDTO;
import com.project.exception.NotFoundException;
import com.project.exception.UnknownException;

@Service
@Transactional
public class MbService {

	private static final Logger logger = LogManager.getLogger(MbService.class);

	@Autowired
	private MbDAO mbDAO;

	// 회원 가입
	public String createMember(MbDTO mbDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String inputId = mbDTO.getId();
		String checkId = inputId.trim();
		String reusltMesg = null;

		Integer createCheckCount = compareNullCheckId(mbDTO, checkId);
		String checkResult = checkResultByInteger(createCheckCount, gusetIP);
		reusltMesg = checkId + "회원가입 " + checkResult;
		grantBasicRole(checkResult, checkId);
		return reusltMesg;
	}

	// 아이디 NULL 대소문자 조합 방지
	private Integer compareNullCheckId(MbDTO mbDTO, String checkId) {
		Integer createCheckCount = null;
		if (!ArrayUtils.contains(ConstantConfig.NULLLIST, checkId)) {
			createCheckCount = mbDAO.createMember(mbDTO);
		} else {
			logger.error("access IP : {} unknown status, retry checkId", checkId);
			throw new UnknownException("비정상적인 값 발생, retry checkId");
		}
		return createCheckCount;
	}

	// BASIC 권한 부여
	private void grantBasicRole(String checkResult, String checkId) {
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
	public String checkIdDuplication(String id) {
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
			UserRole userRole = UserRoleConfig.UserRole.getRole(roleList.get(0).getRoleNum());
			setUser.setRole(userRole.name());
			setUser.setRoleList(roleList);
			setUser.setId(getLogin.getId());
			setUser.setPw(getLogin.getPw());
			setUser.setAls(getLogin.getAls());
			setUser.setNm(getLogin.getNm());
			logger.info("access ID : {}, Login", getLogin.getId());
		} else {
			logger.warn("access ID : {} ,DB is not affected", userIp);
			throw new NotFoundException("DB is not affected. Please check.");
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
		Integer updateCheckCount = mbDAO.updateMyPage(mbDTO);
		String checkResult = checkResultByInteger(updateCheckCount, memberId);
		String reusltMesg = "마이페이지 수정" + checkResult;
		return reusltMesg;
	}

	// 비밀번호 변경
	public String updatePw(UpdatePwDTO updatePwDTO) {
		String memberId = SessionConfig.MbSessionDTO().getId();
		Integer updateCheckCount = mbDAO.updatePw(updatePwDTO);
		String checkResult = checkResultByInteger(updateCheckCount, memberId);
		String reusltMesg = "비밀번호 변경" + checkResult;
		return reusltMesg;
	}

	// 아이디 찾기
	public String searchId(SearchInfoDTO searchInfoDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String searchId = mbDAO.searchId(searchInfoDTO);
		String resultCheck = checkResultByString(gusetIP, searchId);
		String resultMesg = "ID : " + searchId + ", 아이디  찾기 "+ resultCheck;
		return resultMesg;
	}

	// 비밀번호 찾기
	public String searchPw(SearchInfoDTO searchInfoDTO) {
		String gusetIP = IPConfig.getIp(SessionConfig.getSession());
		String searchPw = mbDAO.searchPw(searchInfoDTO);
		String resultCheck = checkResultByString(gusetIP, searchPw);
		String sueccesMesg = "PassWord : " + searchPw + ", 비밀번호  찾기 "+ resultCheck;
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
			throw new NotFoundException("DB is not affected. Please check.");
		}
		return resultMesg;
	}

	// DB 출력값 확인 (문자열)
	private String checkResultByString(String user, String insertId) {
		String resultMesg = null;
		if (!insertId.isEmpty()) {
			resultMesg = ConstantConfig.SUCCESS_MESG_BYSTRING;
		} else if (!insertId.isEmpty()) {
			resultMesg = ConstantConfig.FALSE_MESG_BYSTRING;
		} else {
			logger.error("access IP : {}, unknown status", user);
			throw new NotFoundException("DB is not affected. Please check.");
		}
		return resultMesg;
	}

//	 DB 출력값 확인 (DTO)
	private void checkResultByDTO(MbDTO mbDTO, String gusetIP) {
		if (mbDTO != null) {
			logger.info("access ID : {}, Success", mbDTO.getId());
		} else {
			logger.error("access ID : {}, unknown status", gusetIP);
			throw new NotFoundException("DB is not affected. Please check.");
		}
	}

}
