package io.github.redis.helper.spring;

import io.github.wandering.redis.helper.ratelimit.enums.RateType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author yangyongping
 * 2022-06-14 4:39 下午
 */
@ConfigurationProperties(prefix = "coolcollege.redis.helper")
@Data
public class RedisHelperProperties {
    /**
     * 必填:启动开关: true开启  false关闭
     */
    private boolean enabled;
    private RedisConfig redisConfig = new RedisConfig();
    private RateLimitConfig limitConfig = new RateLimitConfig();

    @Data
    public static class RedisConfig{
        /**
         * redis地址:必填
         */
        private List<String> address;
        /**
         * 密码:选填,默认值:空串
         */
        private String password;
        /**
         * db:选填，默认值: db0
         */
        private Integer db = 0;
    }

    /**
     * 默认值: 单机共享 每5s/10次  2QPS
     *
     */
    @Data
    public static class RateLimitConfig{
        /**
         * 选填:
         * 模式:
         *  OVERALL 全局模式
         *  PER_CLIENT 实例模式(默认值)以注解上位最优先，注解为NON才会选择全局配置
         */
        private RateType rateType = RateType.PER_CLIENT;
        /**
         * 选填:令牌重置周期，以注解上位最优先，注解为-1才会选择全局配置 单位:默认秒，以注解时间单位为准
         */
        private Long rateInterval = 5L;

        /**
         * 选填:每个周期产生的令牌，以注解上位最优先，注解为-1才会选择全局配置
         */
        private Long rate = 10L;
    }
}
