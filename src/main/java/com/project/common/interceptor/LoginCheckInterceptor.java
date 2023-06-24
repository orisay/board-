package com.project.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.config.ConstantConfig;
import com.project.config.ConstantUserRoleConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;
import com.project.exception.UnknownException;

public class LoginCheckInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String roleCheckLogin = SessionConfig.MbSessionDTO().getRole();
		String iDCheckLogin = SessionConfig.getSession().getId();
		String rightCheckLogin = ConstantUserRoleConfig.UserRole.BASIC.name();
		String loginIP = IPConfig.getIp(SessionConfig.getSession());
		if (roleCheckLogin == null) {
			logger.warn("non-role user access and view windows that need session ID : {}, IP : {} "
					, iDCheckLogin, loginIP);
			throw new IllegalArgumentException("non-role user login access");
		}

		if (!rightCheckLogin.equals(roleCheckLogin)) {
			return false;
		} else if (rightCheckLogin.equals(roleCheckLogin)) {
			return true;
		} else {
			logger.error("non-role user access ID : {}, IP : {}", iDCheckLogin, loginIP);
			throw new UnknownException("Login 계정 요청 중 예기치 못한 상태 발생");
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
