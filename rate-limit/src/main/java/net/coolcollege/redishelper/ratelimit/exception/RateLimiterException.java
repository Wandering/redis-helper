package net.coolcollege.redishelper.ratelimit.exception;

/**
 * @author yangyongping
 * @date 2022-06-13 2:26 下午
 */
public class RateLimiterException extends RuntimeException{

    public RateLimiterException(String message) {
        super(message);
    }

}
