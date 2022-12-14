package io.github.wandering.redis.helper.ratelimit.handle;

/**
 * @author yangyongping
 * 2022-06-13 11:26 上午
 * 从这个 RateLimiter 获得许可，阻塞直到有一个可用。
 * 获得许可证，如果有可用许可证并立即返回，则可用许可证的数量减少一个
 */
public class BlockedRateLimiterHandler implements IRateLimiterHandler {

    @Override
    public boolean invoke(RateLimiterContext context) {
        context.getRateLimiter().acquire();
        return true;
    }

}
