package org.bytedeco.javacpp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Adapter("VectorAdapter")
@Retention(RetentionPolicy.RUNTIME)
public @interface StdVector {
    String value() default "";
}
