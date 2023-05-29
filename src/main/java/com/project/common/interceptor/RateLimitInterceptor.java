package com.project.common.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.util.concurrent.RateLimiter;
import com.project.config.ConstantConfig;
import com.project.config.IPConfig;
import com.project.config.SessionConfig;

public class RateLimitInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

	private ConcurrentHashMap<String, RateLimiter> limit = new ConcurrentHashMap<String, RateLimiter>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String accessIP = IPConfig.getIp(SessionConfig.getSession());
		RateLimiter rateLimiter = limit.computeIfAbsent(accessIP, k -> RateLimiter.create(2));
		if (!rateLimiter.tryAcquire()) {
			logger.warn("Too many request accessIP : {}", accessIP);
			response.setStatus(ConstantConfig.SC_TOO_TOO_MANY_REQUESTS);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
