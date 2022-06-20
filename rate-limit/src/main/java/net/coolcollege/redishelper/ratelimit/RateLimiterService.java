package net.coolcollege.redishelper.ratelimit;

import net.coolcollege.redishelper.ratelimit.handle.IRateLimiter;
import net.coolcollege.redishelper.ratelimit.handle.RateLimiterConfig;
import net.coolcollege.redishelper.ratelimit.handle.RedissonRateLimiter;
import org.redisson.api.RedissonClient;

/**
 * @author yangyongping
 * @date 2022-06-13 7:51 下午
 */
public class RateLimiterService {
    private final RedissonClient redissonClient;
    private final RateLimiterConfig defaultRateLimiterConfig;

    public RateLimiterService(RedissonClient redissonClient,RateLimiterConfig rateLimiterConfig) {
        this.redissonClient = redissonClient;
        this.defaultRateLimiterConfig = rateLimiterConfig;
    }

    public IRateLimiter getRateLimiter(String limitKey) {
        return new RedissonRateLimiter(redissonClient.getRateLimiter(limitKey));
    }

    public RateLimiterConfig getDefaultRateLimiterConfig() {
        return defaultRateLimiterConfig;
    }

}
