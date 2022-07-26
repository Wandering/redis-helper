package io.github.redis.helper.ratelimit.handle;

/**
 * @author yangyongping
 * @date 2022-06-13 11:26 上午
 */
public interface IRateLimiterHandler {

    boolean invoke(RateLimiterContext context);

}
