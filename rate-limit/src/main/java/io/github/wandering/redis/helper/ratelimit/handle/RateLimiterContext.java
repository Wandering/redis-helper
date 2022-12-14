package io.github.wandering.redis.helper.ratelimit.handle;

import java.lang.reflect.Method;

/**
 * @author yangyongping
 * 2022-06-14 3:50 下午
 */

public class RateLimiterContext {
    private final String limitKey;
    private final Method method;
    private final Object[] args;
    private final IRateLimiter rateLimiter;
    private final RateLimiterConfig rateLimiterConfig;

    public RateLimiterContext(String limitKey, Method method, Object[] args, IRateLimiter rateLimiter,
        RateLimiterConfig rateLimiterConfig) {
        this.limitKey = limitKey;
        this.method = method;
        this.args = args;
        this.rateLimiter = rateLimiter;
        this.rateLimiterConfig = rateLimiterConfig;
    }

    public String getLimitKey() {
        return limitKey;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public IRateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public RateLimiterConfig getRateLimiterConfig() {
        return rateLimiterConfig;
    }

}
