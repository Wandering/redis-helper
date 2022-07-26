package net.coolcollege.redishelper.ratelimit.handle;

import net.coolcollege.redishelper.ratelimit.RateLimiterService;
import net.coolcollege.redishelper.ratelimit.annotation.RateLimiter;
import net.coolcollege.redishelper.ratelimit.enums.RateType;
import net.coolcollege.redishelper.ratelimit.exception.RateLimiterException;
import net.coolcollege.redishelper.ratelimit.spel.SpringElParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author yangyongping
 * @date 2022-06-13 11:25 上午
 */
@Aspect
public class RateLimiterExecutor {

    private RateLimiterService rateLimiterService;
    private static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    /**SPEL表达式标识符*/
    public final String SPEL_FLAG = "#";
    public final String KEY_SEPARATE = ".";

    public RateLimiterExecutor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }



    @Pointcut("@annotation(net.coolcollege.redishelper.ratelimit.annotation.RateLimiter)")
    public void pointcut(){
    }

    @Around(value = "pointcut()")
    public void before(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        Method method = signature.getMethod();
        Object[] args = point.getArgs();
        String[] params = discoverer.getParameterNames(method);

        RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);

        String methodName = method.getName();
        String rRateLimiterKey = getLimiterKeyOrDefault(rateLimiter,declaringTypeName, methodName,args,params);
        long rate = getRateOrDefault(rateLimiter);
        long rateInterval = getRateIntervalOrDefault(rateLimiter);
        TimeUnit timeUnit = rateLimiter.timeUnit();
        RateType rateType = getRateTypeOrDefault(rateLimiter);

        // 创建
        IRateLimiter limiter = rateLimiterService.getRateLimiter(rRateLimiterKey);

        RateLimiterConfig rateLimiterConfig = new RateLimiterConfig(rateType,rateInterval,rate, timeUnit);
        // 初始化限流器
        limiter.trySetRate(rateLimiterConfig);

        // 抓取令牌
        boolean acquire = limiter.tryAcquire();

        if (acquire) {
            // 拿到令牌了 继续执行方法
            point.proceed();
            return;
        }
        // 没拿到令牌，不进行后续逻辑，走处理类
        Object o = rateLimiter.raterLimiterHandler().newInstance();
        if (o instanceof IRateLimiterHandler) {
            IRateLimiterHandler rateLimiterHandler = (IRateLimiterHandler)o;
            acquire = rateLimiterHandler.invoke(new RateLimiterContext(rRateLimiterKey,method,args,limiter,rateLimiterConfig));
            if (acquire){
                //执行实际方法
                point.proceed();
                return;
            }
            return;
        } else {
            throw new RateLimiterException("the processing class is incorrect, and the current limit does not take effect");
        }

    }

    private String getLimiterKeyOrDefault(RateLimiter rateLimiter, String declaringTypeName, String methodName, Object[] args, String[] params) {
        String rRateLimiterKey = rateLimiter.key();

        if (StringUtils.isEmpty(rRateLimiterKey)){
            rRateLimiterKey = methodName;
        }
        rRateLimiterKey = this.analysisSpel(rRateLimiterKey,args,params);
        return buildKey(declaringTypeName,rRateLimiterKey);
    }

    private String buildKey(String declaringTypeName,String rRateLimiterKey){
        StringBuffer stringBuffer = new StringBuffer(declaringTypeName);
        return stringBuffer.append(rRateLimiterKey).append(KEY_SEPARATE).toString();
    }

    private long getRateIntervalOrDefault(RateLimiter rateLimiter) {
        RateLimiterConfig defaultRateLimiterConfig = rateLimiterService.getDefaultRateLimiterConfig();
        // 修正rateInterval
        long rateInterval = rateLimiter.rateInterval();
        return (rateInterval == -1 ? defaultRateLimiterConfig.getRateInterval():rateInterval);

    }

    private long getRateOrDefault(RateLimiter rateLimiter) {
        RateLimiterConfig defaultRateLimiterConfig = rateLimiterService.getDefaultRateLimiterConfig();
        long rate = rateLimiter.rate();
        return (rate == -1 ? defaultRateLimiterConfig.getRate():rate);
    }

    private RateType getRateTypeOrDefault(RateLimiter rateLimiter) {
        RateLimiterConfig defaultRateLimiterConfig = rateLimiterService.getDefaultRateLimiterConfig();
        RateType rateType = rateLimiter.rateType();
        return (RateType.NON.equals(rateType) ? defaultRateLimiterConfig.getRateType():rateType);
    }

    /**
     * 解析SPEL表达式
     *
     * @author 曲元涛
     * @date 2020/11/30 下午6:05
     * @param spel          SPEL表达式
     */
    private String analysisSpel(String spel, Object[] args,String[] params) {
        if (!spel.contains(SPEL_FLAG)) {
            return spel;
        }
        return SpringElParser.parse(String.class,args,params,spel);
    }

}
