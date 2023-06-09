package com.project.config;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.config.ConstantConfig.UserRole;
import com.project.dto.MbSessionDTO;

public class SessionConfig {

	// 세션관리 + ip 저장
	public static HttpSession getSession() {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = httpServletRequest.getSession();
		IPConfig.setIp(session);
		return session;
	}

	// service에서 처리를 위해서 설정
	public static MbSessionDTO MbSessionDTO() {
		HttpSession session = getSession();
		MbSessionDTO mbSessionDTO = new MbSessionDTO();
//				(MbSessionDTO) session.getAttribute(ConstantConfig.Member_INFO);

		mbSessionDTO.setId("admin");
		mbSessionDTO.setRole(UserRole.ADMIN.name());
		return mbSessionDTO;
	}

	// view에 사용 할 마지막 클릭 조회
	public static LocalDateTime getLastClick() {
		HttpSession session = getSession();
		LocalDateTime lastClick = (LocalDateTime) session.getAttribute(ConstantConfig.LastClick);
		return lastClick;
	}

	// 마지막 클릭 시간 업데이트
	public static void updateLastClickTime() {
		HttpSession session = getSession();
		session.setAttribute(ConstantConfig.LastClick, LocalDateTime.now());
	}

}
