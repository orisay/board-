package com.project.config;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.util.concurrent.RateLimiter;
import com.project.dto.MbDTO;

public class SessionConfig {

	private ConcurrentHashMap<String, RateLimiter> limit = new ConcurrentHashMap<String, RateLimiter>();

	// 세션관리
	public static HttpSession getSession() {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = httpServletRequest.getSession();
		return session;
	}

	public static MbDTO getMbDTO() {
		HttpSession session = getSession();
		MbDTO mbDTO = (MbDTO) session.getAttribute(ConstantConfig.Member);
		return mbDTO;
	}

	public static LocalDateTime getLastClick() {
		HttpSession session = getSession();
		LocalDateTime lastClick = (LocalDateTime) session.getAttribute(ConstantConfig.LastClick);
		return lastClick;
	}

	public static void updateLastClickTime() {
		HttpSession session = getSession();
		session.setAttribute(ConstantConfig.LastClick, LocalDateTime.now());
	}

	public boolean tryAcquire(String ip) {
		RateLimiter rateLimiter = limit.computeIfAbsent(ip, k -> RateLimiter.create(0.2));
		return rateLimiter.tryAcquire();
	}

}
