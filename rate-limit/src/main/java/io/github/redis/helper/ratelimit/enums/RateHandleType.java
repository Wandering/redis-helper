package io.github.redis.helper.ratelimit.enums;

/**
 * @author yangyongping
 * 2022-06-13 11:11 上午
 * 限流处理策略:
 * 1. 中断当前请求
 * 2. 阻塞一会重试
 */
public enum RateHandleType {
    /**
     * 阻塞一会重试
     */
    BLOCKED,
    /**
     * 停止当前请求
     */
    STOP
}
