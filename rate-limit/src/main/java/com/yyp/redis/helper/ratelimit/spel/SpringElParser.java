package com.yyp.redis.helper.ratelimit.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author yangyongping
 * @date 2022-06-14 8:44 下午
 */
public class SpringElParser {
    private static final ExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     * EL表达式解析
     *
     * @param clazz     返回值类型
     * @param elStr     EL表达式
     * @return 解析结果
     */
    public static <T> T parse(Class<T> clazz, Object[] args,String[] params, String elStr) {
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < (params != null ? params.length : 0); len++) {
            context.setVariable(params[len], args[len]);
        }
        return spelExpressionParser.parseExpression(elStr).getValue(context, clazz);
    }
}
