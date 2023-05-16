package com.project.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class IPConfig {

	public static void setIp() {
		HttpSession session = SessionConfig.getSession();
		String clientIp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getRemoteAddr();
		session.setAttribute(ConstantConfig.clientIp, clientIp);
	}
	public static String getIp(HttpServletRequest request) {
		HttpSession session = SessionConfig.getSession();
		String clientIp = (String) session.getAttribute(ConstantConfig.clientIp);
		return clientIp;
	}

}
