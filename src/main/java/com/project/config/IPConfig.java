package com.project.config;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.common.constant.ConstantConfig;

//진짜 단순하게 ip를 넣고 뺴고 끝
public class IPConfig {

	public static void setIp(HttpSession session) {
		String clientIp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getRemoteAddr();
		session.setAttribute(ConstantConfig.clientIp, clientIp);
	}
	public static String getIp(HttpSession session) {
		String clientIp = (String) session.getAttribute(ConstantConfig.clientIp);
		return clientIp;
	}


}
