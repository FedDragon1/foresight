package cf.epiphania.foresight.util.aspect;

import cf.epiphania.foresight.util.LoggerDispatcher;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
@Profile({"dev", "test"})
public class WebInfoAspect extends LoggerDispatcher {
    private final Gson gson = new Gson();
    @Pointcut("@annotation(cf.epiphania.foresight.util.aspect.WebInfo)")
    public void webInfo() {}

    @Around("webInfo()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();  // 执行切点
        logger.info("\033[36mReturn     \033[0m - \033[92m{}\033[0m", gson.toJson(result));
        logger.info("\033[36mTime       \033[0m - \033[92m{}ms\033[0m", System.currentTimeMillis() - startTime);
        logger.info("\033[92m=========================================\033[31m[END]\033[92m=========================================\033[0m");
        return result;
    }

    @Before("webInfo()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        logger.info("\033[92m========================================\033[31m[BEGIN]\033[92m========================================\033[0m");
        logger.info("\033[36mURL        \033[0m - \033[92m{}\033[0m", request.getRequestURL().toString());
        logger.info("\033[36mHTTP Method\033[0m - \033[92m{}\033[0m", request.getMethod());
        logger.info("\033[36mMethod     \033[0m - \033[92m{}.{}\033[0m", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        logger.info("\033[36mDescription\033[0m - \033[92m{}\033[0m", getDescription(joinPoint));
        logger.info("\033[36mIP         \033[0m - \033[92m{}\033[0m", request.getRemoteAddr());
        logger.info("\033[36mParameters \033[0m - \033[92m{}\033[0m", gson.toJson(joinPoint.getArgs()));
    }

    public static void main(String[] args) {
        WebInfoAspect webInfoAspect = new WebInfoAspect();
        webInfoAspect.logger.info("\033[92m========================================\033[31m[BEGIN]\033[92m========================================\033[0m");
        webInfoAspect.logger.info("\033[92m=========================================\033[31m[END]\033[92m=========================================\033[0m");

    }

    public String getDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        String tName = joinPoint.getTarget().getClass().getName();
        String mName = joinPoint.getSignature().getName();
        int argsCount = joinPoint.getArgs().length;

        for (Method method: Class.forName(tName).getDeclaredMethods()) {
            if (method.getName().equals(mName) && method.getParameterTypes().length == argsCount) {
                return method.getAnnotation(WebInfo.class).description();
            }
        }

        throw new IllegalArgumentException("getDescription called without @WebInfo annotation");
    }
}
