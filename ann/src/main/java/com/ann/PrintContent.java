package com.ann;

import java.lang.annotation.*;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 17:43
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintContent {
    String value();
}