package com.test;

/**
 * @author: create by wangmh
 * @projectName: java8_新特性
 * @packageName: com.test
 * @description: 函数接口 只有一个抽象方法的接口，否则编译会报错
 * @date: 2021/2/18
 **/
@FunctionalInterface
public interface FunInterface {
    String work(String a);

    /***
     * 默认方法
     * @return
     */
    default String test() {
        return "默认方法";
    }

    /***
     * 静态默认方法
     * @return
     */
    static String test1() {
        return "静态默认方法";
    }
}
