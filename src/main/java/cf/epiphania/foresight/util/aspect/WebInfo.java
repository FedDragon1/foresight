package cf.epiphania.foresight.util.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WebInfo {
    String description() default "";
}

