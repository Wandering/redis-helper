package net.coolcollege.redishelper.demo.redishelperdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
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
        for (int i = 0; i < 100; i++) {
            rateLimitTest.rateLimit();
        }

        Assertions.assertEquals(rateLimitTest.size(),1);
    }


    @Test
    void testSpelRateLimit(){
        for (int i = 0; i < 100; i++) {
            rateLimitTest.rateLimitSpel("testKey");
        }

        Assertions.assertEquals(rateLimitTest.size(),1);
    }
}
