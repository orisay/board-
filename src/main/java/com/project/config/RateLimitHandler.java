//package com.project.config;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.google.common.util.concurrent.RateLimiter;
//import com.project.service.BoardService;
//
//public class RateLimitHandler {
//    private static final Logger logger = LogManager.getLogger(BoardService.class);
//
//    private ConcurrentHashMap<String, RateLimiter> limit = new ConcurrentHashMap<String, RateLimiter>();
//
//    public boolean tryAcquire(String ip) {
//        RateLimiter rateLimiter = limit.computeIfAbsent(ip, k -> RateLimiter.create(0.2));
//        return rateLimiter.tryAcquire();
//    }
//
//    public boolean checkRateLimit(HttpServletRequest request) {
////        String clientIp = IPConfig.getIp(request);
//        boolean isAllowed = tryAcquire(clientIp);
//        boolean mesg = true;
//        if (!isAllowed) {
//            logger.info("Too many request ip " + clientIp);
//            mesg = false;
//        }
//        return mesg;
//    }
//}