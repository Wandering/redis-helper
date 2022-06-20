package net.coolcollege.redishelper.ratelimit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.springframework.core.annotation.AliasFor;

import net.coolcollege.redishelper.ratelimit.enums.RateType;

/**
 * @author yangyongping
 * @date 2022-06-13 11:07 上午
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RateLimiter(rateType = RateType.OVERALL)
public @interface GlobalRateLimiter {

}
