package net.coolcollege.redishelper.demo.redishelperdemo;

import net.coolcollege.redishelper.ratelimit.annotation.RateLimiter;
import net.coolcollege.redishelper.ratelimit.enums.RateType;
import net.coolcollege.redishelper.ratelimit.handle.BlockedRateLimiterHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangyongping
 * @date 2022-06-14 5:55 下午
 */
@Component
public class RateLimitTest {
    private AtomicInteger atomicInteger = new AtomicInteger();

    @RateLimiter(rate = 1,rateInterval = 10,rateType= RateType.OVERALL)
    public void rateLimit(){
        System.out.println(atomicInteger.incrementAndGet());
    }

    @RateLimiter(rate = 1,rateInterval = 10,rateType= RateType.OVERALL,raterLimiterHandler= BlockedRateLimiterHandler.class)
    public void rateLimitBlock(){
        System.out.println(atomicInteger.incrementAndGet());
    }

    @RateLimiter(key = "#limitKey",rate = 1,rateInterval = 10,rateType= RateType.OVERALL)
    public void rateLimitSpel(String limitKey){
        System.out.println(atomicInteger.incrementAndGet());
    }

    public int size(){
        return atomicInteger.get();
    }
}
