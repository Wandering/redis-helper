# redis帮助组件
## 限流组件
[限流组件](rate-limit-spring-boot-starter "限流组件")

# 限流组件
## 引入组件包
```xml
<dependency>
    <artifactId>rate-limit-spring-boot-starter</artifactId>
    <groupId>io.github.wandering</groupId>
    <version>1.0.0-RELEASE</version>
</dependency>
```
## 在springboot配置中新增配置
```properties
#必填:启动开关: true开启  false关闭
coolcollege.redis.helper.enabled=true
#redis地址:必填
coolcollege.redis.helper.redis-config.address=redis://127.0.0.1:6379
#密码:选填,默认值:空串
coolcollege.redis.helper.redis-config.password=
#选填:模式:OVERALL 全局模式 PER_CLIENT 实例模式(默认值)以注解上位最优先，注解为NON才会选择全局配置
coolcollege.redis.helper.limit-config.rate-type=per_client
#选填:每个周期产生的令牌，以注解上位最优先，注解为-1才会选择全局配置
coolcollege.redis.helper.limit-config.rate=5
#选填:令牌重置周期，以注解上位最优先，注解为-1才会选择全局配置 单位:默认秒，以注解时间单位为准
coolcollege.redis.helper.limit-config.rate-interval=5
```
## 使用
```java
@Component
public class RateLimitTest {
    private AtomicInteger atomicInteger = new AtomicInteger();

    @RateLimiter(rate = 1,rateInterval = 10,rateType= RateType.OVERALL)
    public void rateLimit(){
        System.out.println(atomicInteger.incrementAndGet());
    }

    @RateLimiter(rate = 1,rateInterval = 10,rateType= RateType.OVERALL,raterLimiterHandler= BlockedRateLimiterHandler.class)
    public void rateLimitBlock(){
        System.out.println(atomicInteger.incrementAndGet());
    }

    @RateLimiter(key = "#limitKey",rate = 1,rateInterval = 10,rateType= RateType.OVERALL)
    public void rateLimitSpel(String limitKey){
        System.out.println(atomicInteger.incrementAndGet());
    }

    public int size(){
        return atomicInteger.get();
    }
}


```

