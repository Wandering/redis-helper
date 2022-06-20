package net.coolcollege.redishelper.ratelimit.handle;

import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * @date 2022-06-14 4:01 下午
 */
public class RedissonRateLimiter implements IRateLimiter {

    private final RRateLimiter rateLimiter;
    private RateLimiterConfig rateLimiterConfig;

    public RedissonRateLimiter(RRateLimiter rRateLimiter) {
        this.rateLimiter = rRateLimiter;
    }

    @Override
    public boolean trySetRate(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
        return rateLimiter.trySetRate(RateType.valueOf(rateLimiterConfig.getRateType().toString()),rateLimiterConfig.getRate(),rateLimiterConfig.getRateInterval(),
            RateIntervalUnit.valueOf(rateLimiterConfig.getTimeUnit().toString()));
    }

    @Override
    public void setRate(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
        rateLimiter.setRate(RateType.valueOf(rateLimiterConfig.getRateType().toString()),rateLimiterConfig.getRate(),rateLimiterConfig.getRateInterval(),
            RateIntervalUnit.valueOf(rateLimiterConfig.getTimeUnit().toString()));
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

    @Override
    public boolean tryAcquire(long permits) {
        return rateLimiter.tryAcquire(permits);
    }

    @Override
    public void acquire() {
        rateLimiter.acquire();
    }

    @Override
    public void acquire(long permits) {
        rateLimiter.acquire();
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(timeout,unit);
    }

    @Override
    public boolean tryAcquire(long permits, long timeout, TimeUnit unit) {
        return rateLimiter.tryAcquire(permits,timeout,unit);
    }

    @Override
    public RateLimiterConfig getConfig() {
        return rateLimiterConfig;
    }

    @Override
    public long availablePermits() {
        return rateLimiter.availablePermits();
    }

}
