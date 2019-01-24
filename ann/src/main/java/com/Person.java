package com;

import com.ann.PrintContent;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 17:08
 */
public interface Person {

    @PrintContent("来自注解 PrintContent 唱歌")
    void sing(String value);

    @PrintContent("来自注解 PrintContent 跑步")
    void run(String value);

    @PrintContent("来自注解 PrintContent 吃饭")
    void eat(String value);

    @PrintContent("来自注解 PrintContent 工作")
    void work(String value);
}
