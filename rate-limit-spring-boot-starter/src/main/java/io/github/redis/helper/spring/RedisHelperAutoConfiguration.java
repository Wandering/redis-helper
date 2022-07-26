package io.github.redis.helper.spring;

import io.github.redis.helper.ratelimit.RateLimiterService;
import lombok.extern.slf4j.Slf4j;
import io.github.redis.helper.ratelimit.handle.RateLimiterConfig;
import io.github.redis.helper.ratelimit.handle.RateLimiterExecutor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * 2022-06-14 4:39 下午
 */
@Slf4j
@Configuration
// 引入配置文件类
@EnableConfigurationProperties(RedisHelperProperties.class)
@ConditionalOnProperty(prefix = "coolcollege.redis.helper",name = "enabled", havingValue = "true")
public class RedisHelperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config redissonConfig(RedisHelperProperties redisHelperProperties) {
        Config config = new Config();
        ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
        RedisHelperProperties.RedisConfig redisConfig = redisHelperProperties.getRedisConfig();
        if (!CollectionUtils.isEmpty(redisConfig.getAddress())) {
            replicatedServersConfig.setNodeAddresses(redisConfig.getAddress());
            if (!StringUtils.isEmpty(redisConfig.getPassword())) {
                replicatedServersConfig.setPassword(redisConfig.getPassword());
                replicatedServersConfig.setDatabase(redisConfig.getDb());
            }
        }
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(Config redissonConfig) {
        return Redisson.create(redissonConfig);
    }

    @Bean
    @ConditionalOnMissingBean(RateLimiterService.class)
    public RateLimiterService rateLimiterService(RedissonClient redissonClient,RedisHelperProperties redisHelperProperties) {
        log.info("rateLimiter activating");
        RedisHelperProperties.RateLimitConfig limitConfig = redisHelperProperties.getLimitConfig();
        return new RateLimiterService(redissonClient, new RateLimiterConfig(limitConfig.getRateType(),limitConfig.getRateInterval(),limitConfig.getRate(),
            TimeUnit.SECONDS));
    }


    @Bean
    @ConditionalOnBean(RateLimiterService.class)
    public RateLimiterExecutor rateLimiterExecutor(RateLimiterService rateLimiterService){
        return new RateLimiterExecutor(rateLimiterService);
    }
}
