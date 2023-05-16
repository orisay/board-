package com.project.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.RateLimiter;
import com.project.service.BoardService;

public class RateLimitHandler {

	private static final Logger logger = LogManager.getLogger(BoardService.class);

	private static final double DEFAULT_RATE_LIMIT = 0.2;
	private RateLimiter rateLimiter;

	public RateLimitHandler() {
		this.rateLimiter = RateLimiter.create(DEFAULT_RATE_LIMIT);
	}

	public boolean tryAcquire(String ip) {
		return rateLimiter.tryAcquire();
	}

	public boolean checkRateLimit(HttpServletRequest request) {
		String clientIp = IPConfig.getIp(request);
		boolean isAllowed = tryAcquire(clientIp);
		boolean mesg = true;
		if (!isAllowed) {
			logger.info("Too many request ip " + clientIp);
			mesg = false;
		}
		return mesg;
	}
}
