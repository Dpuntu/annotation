package com.ann;

/**
 * @author: fangmingxing
 * @date: 2019-01-24
 */

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Prinln1 {
    int value() default 0;
}
