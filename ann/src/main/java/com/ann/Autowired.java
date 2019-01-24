package com.ann;

import java.lang.annotation.*;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 17:06
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
