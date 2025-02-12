package kr.co.digitalship.msarouter.aspect;

import kr.co.digitalship.msarouter.log.LogApplication;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
@RequiredArgsConstructor
public class SaveLogAspect {

    private final LogApplication logApp;

    @Around("@annotation(log)")
    public Object saveLog(ProceedingJoinPoint joinPoint, SaveLog log) throws Throwable {
        try {
            if (!log.before().isEmpty()) logApp.saveLog(log.before());
            Object result = joinPoint.proceed();
            if (!log.after().isEmpty()) logApp.saveLog(log.after());
            return result;
        } catch (Throwable e) {
            throw e;
        }

    }

}
