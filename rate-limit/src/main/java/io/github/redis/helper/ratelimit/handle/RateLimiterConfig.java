package io.github.redis.helper.ratelimit.handle;

import io.github.redis.helper.ratelimit.enums.RateType;
import lombok.EqualsAndHashCode;

import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * 2022-06-13 7:52 下午
 */
@EqualsAndHashCode
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
