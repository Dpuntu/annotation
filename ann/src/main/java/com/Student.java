package com;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 17:09
 */
public class Student implements Person {

    @Override
    public void sing(String value) {
        System.out.println(value == null ? "这是音乐课，我们在唱歌" : value);
    }

    @Override
    public void run(String value) {
        System.out.println(value == null ? "这是体育课，我们在跑步" : value);
    }

    @Override
    public void eat(String value) {
        System.out.println(value == null ? "中午我们在食堂吃饭" : value);
    }

    @Override
    public void work(String value) {
        System.out.println(value == null ? "我们的工作是学习" : value);
    }
}
