package net.coolcollege.redishelper.ratelimit.handle;

import net.coolcollege.redishelper.ratelimit.enums.RateType;

import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * @date 2022-06-13 7:52 下午
 */
public class RateLimiterConfig {
    private RateType rateType;
    private long rateInterval;
    private long rate;
    private TimeUnit timeUnit;

    public RateLimiterConfig(RateType rateType, long rateInterval, long rate,TimeUnit timeUnit) {
        super();
        this.rateType = rateType;
        this.rateInterval = rateInterval;
        this.rate = rate;
        this.timeUnit = timeUnit;
    }


    public RateType getRateType() {
        return rateType;
    }


    public long getRateInterval() {
        return rateInterval;
    }


    public long getRate() {
        return rate;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

}