package com.yyp.redis.helper.demo;

import com.yyp.redis.helper.ratelimit.exception.RateLimiterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisHelperDemoApplicationTests {

    @Test
    void contextLoads() {}

    @Autowired
    private RateLimitTest rateLimitTest;

    @Test
    void testSingleRateLimit(){
        rateLimitTest.rateLimit();
        Assertions.assertEquals(rateLimitTest.size(),1);
        Assertions.assertThrows(RateLimiterException.class,()->rateLimitTest.rateLimit());

    }


    @Test
    void testSpelRateLimit(){
        for (int i = 0; i < 100; i++) {
            rateLimitTest.rateLimitSpel("testKey");
        }

        Assertions.assertEquals(rateLimitTest.size(),1);
    }
}
