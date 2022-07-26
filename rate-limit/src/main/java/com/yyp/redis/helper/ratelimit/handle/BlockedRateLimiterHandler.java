package com.yyp.redis.helper.ratelimit.handle;

/**
 * @author yangyongping
 * @date 2022-06-13 11:26 上午
 */
public class BlockedRateLimiterHandler implements IRateLimiterHandler {

    @Override
    public boolean invoke(RateLimiterContext context) {
        context.getRateLimiter().acquire();
        return true;
    }

}
