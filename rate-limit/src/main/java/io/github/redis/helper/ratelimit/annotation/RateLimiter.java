package io.github.redis.helper.ratelimit.annotation;

import io.github.redis.helper.ratelimit.enums.RateType;
import io.github.redis.helper.ratelimit.handle.DefaultRateLimiterHandler;
import io.github.redis.helper.ratelimit.handle.IRateLimiterHandler;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * @date 2022-06-13 11:07 上午
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流键值 默认取方法的全限定名作为键值
     * @return
     */
    @AliasFor("key")
    String value() default "";

    /**
     * 限流键值
     * @return
     */
    @AliasFor("value")
    String key() default "";

    /**
     * 限流类型: 默认每个客户端单独计算速率
     * @return
     */
    RateType rateType() default RateType.PER_CLIENT;

    /**
     * 每个周期产生令牌数
     * 此侦听器的目标名称，通过容器范围的{@link org.springframework.jms.support.destination.DestinationResolver}策略解析。
     * @return
     */
    long rate() default -1;

    /**
     * 时间周期
     * @return
     */
    long rateInterval() default -1;

    /**
     * 时间单位,默认:秒
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 实现了 {@link IRateLimiterHandler}的接口，当出现被限流时会进入方法的invoke方法
     * 默认值 抛出一个异常并丢弃执行后续方法
     * @return
     */
    Class raterLimiterHandler() default DefaultRateLimiterHandler.class;
}
