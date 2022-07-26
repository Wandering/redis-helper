package io.github.wandering.redis.helper.ratelimit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.wandering.redis.helper.ratelimit.enums.RateType;

/**
 * @author yangyongping
 * 2022-06-13 11:07 上午
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RateLimiter(rateType = RateType.OVERALL)
public @interface GlobalRateLimiter {

}
