package io.github.redis.helper.ratelimit;

import io.github.redis.helper.ratelimit.handle.RedissonRateLimiter;
import io.github.redis.helper.ratelimit.handle.IRateLimiter;
import io.github.redis.helper.ratelimit.handle.RateLimiterConfig;
import org.redisson.api.RedissonClient;

/**
 * @author yangyongping
 * 2022-06-13 7:51 下午
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
