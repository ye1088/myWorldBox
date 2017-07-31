package org.bytedeco.javacpp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Platform {
    String[] cinclude() default {};

    String[] compiler() default {};

    String[] define() default {};

    String[] framework() default {};

    String[] frameworkpath() default {};

    String[] include() default {};

    String[] includepath() default {};

    String library() default "";

    String[] link() default {};

    String[] linkpath() default {};

    String[] not() default {};

    String[] preload() default {};

    String[] preloadpath() default {};

    String[] value() default {};
}
