package guzenkov.sbertask;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property{
    String name();
    String defaultValue() default "0";
}