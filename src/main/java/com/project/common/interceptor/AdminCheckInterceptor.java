package com.project.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.common.constant.UserRoleConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.exception.UnknownException;

public class AdminCheckInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AdminCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String roleCheckAdmin = SessionConfig.MbSessionDTO().getRole();
		String iDCheckAdmin = SessionConfig.getSession().getId();
		String rightCheckAdmin = UserRoleConfig.UserRole.ADMIN.name();
		String adminIP = IPConfig.getIp(SessionConfig.getSession());
		if (roleCheckAdmin == null) {
			logger.warn("non-role user access and view windows that need session ID : {}, IP : {} "
					, iDCheckAdmin, adminIP);
			throw new IllegalArgumentException("non-role user admin access");
		}

		if (!rightCheckAdmin.equals(roleCheckAdmin)) {
			return false;
		} else if (rightCheckAdmin.equals(roleCheckAdmin)) {
			return true;
		} else {
			logger.error("non-role user access ID : {}, IP : {}", iDCheckAdmin, adminIP);
			throw new UnknownException("admin 계정 요청 중 예기치 못한 상태 발생");
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
