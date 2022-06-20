package net.coolcollege.redishelper.ratelimit.handle;

import net.coolcollege.redishelper.ratelimit.handle.RateLimiterContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangyongping
 * @date 2022-06-13 11:26 上午
 */
public interface IRateLimiterHandler {

    boolean invoke(RateLimiterContext context);

}
