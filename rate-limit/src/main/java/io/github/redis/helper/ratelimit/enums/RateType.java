package io.github.redis.helper.ratelimit.enums;

/**
 * @author yangyongping
 * @date 2022-06-13 11:11 上午
 */
public enum RateType {
    /**
     * Total rate for all RateLimiter instances
     */
    OVERALL,

    /**
     * Total rate for all RateLimiter instances working with the same Redisson instance
     */
    PER_CLIENT,

    /**
     * 未设置，使用全局设置
     */
    NON
}
