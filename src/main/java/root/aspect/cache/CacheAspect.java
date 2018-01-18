package root.aspect.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Aspect
public class CacheAspect {
    private static HashMap<Integer, Integer> cache = new HashMap<Integer, Integer>();

    @Around("@annotation(root.aspect.cache.Cache) && execution(int root.Point.get*()) && !within(CacheAspect)")
    public int caching(ProceedingJoinPoint joinPoint) {
        try {
            System.out.println("in caching() method");
            int key = joinPoint.getTarget().hashCode() * joinPoint.getSignature().getName().hashCode();
            if (!cache.containsKey(key)) {
                int result = (Integer) joinPoint.proceed();
                cache.put(key, result);
            }
            return cache.get(key);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return 0;
        }
    }

    @Around("@annotation(root.aspect.cache.Cache) && execution(void root.Point.set*(int)) && !within(CacheAspect)")
    public int clearCache(ProceedingJoinPoint joinPoint) {
        System.out.println("in clearCache() method");
        int key = joinPoint.getTarget().hashCode() * joinPoint.getSignature().getName().replace('s','g').hashCode();
        if(cache.containsKey(key))
            cache.remove(key);
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }
}
