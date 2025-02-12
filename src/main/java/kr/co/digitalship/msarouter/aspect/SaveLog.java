package kr.co.digitalship.msarouter.aspect;

public @interface SaveLog {
    String before() default "";

    String after() default "";
}
