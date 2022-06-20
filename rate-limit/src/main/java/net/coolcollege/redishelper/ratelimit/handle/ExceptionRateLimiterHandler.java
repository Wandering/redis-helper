package net.coolcollege.redishelper.ratelimit.handle;

import net.coolcollege.redishelper.ratelimit.exception.RateLimiterException;

/**
 * @author yangyongping
 * @date 2022-06-13 11:26 上午
 */
public class ExceptionRateLimiterHandler implements IRateLimiterHandler {

    @Override
    public boolean invoke(RateLimiterContext context) {
        throw new RateLimiterException(String.format("flow control reaches limit frequency",context.getLimitKey()));
    }

}
