package io.github.wandering.redis.helper.ratelimit.handle;

/**
 * @author yangyongping
 * 2022-06-13 11:26 上午
 */
public interface IRateLimiterHandler {

    boolean invoke(RateLimiterContext context);

}
