package com.test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: create by wangmh
 * @projectName: java8_新特性
 * @packageName: com.test
 * @description:
 * @date: 2021/2/18
 **/
public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {

        //1.函数接口 2.默认方法 静态默认方法
        test((item) -> item);

        //3.Stream流
        test2();
        //高级集合类及收集器
        test3();

        //4.方法引用
        //引用的方法的参数列表必须与实现的抽象方法参数列表保持一致
        test4();

        //5.日期时间 API
        test5();

        //6.Base64
        test6();

        //7.Optional 类
        test7();
    }

    public static String getStaticMethod(String a) {
        System.out.println("我是 getMethod");
        return "我是 getMethod";
    }


    //Optional 类
    private static void test7() {
        System.out.println("------Optional 类");
        String name = "王明欢";
        //1.返回空的 Optional 实例。
        Optional<Object> empty = Optional.empty();

        //2.判断是否等于 Optional。
        boolean equals = Optional.of(name).equals(Optional.of(name));
        System.out.println(equals);

        //3.of ofNullable
        //of 如果值为null会抛出异常
        try {
            Optional<Object> of = Optional.of(null);
        } catch (NullPointerException e) {
            System.out.println("Optional.of()抛出的空指针异常");
        }
        //ofNullable 如果为null不会抛出异常
        Optional<Object> ofNullable = Optional.ofNullable(null);

        //4.验证是否有值 方法一
        if (ofNullable.isPresent()) {
            System.out.println(ofNullable.get());
        }
        //验证是否有值 方法二  执行检查，还接受一个Consumer(消费者) 参数
        ofNullable.ifPresent(u -> System.out.println(u));

        //5.为null返回默认值 方法一
        Object o = Optional.ofNullable(null).orElse("默认值1");
        System.out.println(o);
        //为null返回默认值 方法二
        Object o1 = Optional.ofNullable(null).orElseGet(() -> "默认值2");
        System.out.println(o1);

        //6.返回异常 为null抛出异常
        Object o3 = Optional.ofNullable("null").orElseThrow(() -> new NullPointerException());

        //7.转换值  Option中 map flatMap filter使用
        Optional.ofNullable(name).map(item -> item).orElse("默认值");

        //8.Optional 类的链式方法 略
    }


    //Base64
    private static void test6() throws UnsupportedEncodingException {
        // 编码
        String base64encodedString = Base64.getEncoder().encodeToString("哈哈哈哈哈".getBytes("utf-8"));
        System.out.println("Base64 编码字符串 (基本) :" + base64encodedString);

        // 解码
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("原始字符串: " + new String(base64decodedBytes, "utf-8"));


        base64encodedString = Base64.getUrlEncoder().encodeToString("runoob?java8".getBytes("utf-8"));
        System.out.println("Base64 编码字符串 (URL) :" + base64encodedString);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; ++i) {
            stringBuilder.append(UUID.randomUUID().toString());
        }

        byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
        String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
        System.out.println("Base64 编码字符串 (MIME) :" + mimeEncodedString);
    }

    /**
     * 日期时间 API
     */
    private static void test5() {
        System.out.println("------------日期时间 API");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        //LocalDateTime->LocalDate
        LocalDate localDate1 = localDateTime.toLocalDate();

        //年
        int year = localDate1.getYear();
        System.out.println(year);
        //月 真实月份 1-12
        int monthValue = localDate1.getMonthValue();
        System.out.println(monthValue);
        //日
        int dayOfMonth = localDate1.getDayOfMonth();
        System.out.println(dayOfMonth);

        //修改日期中的某个值 比如年 月中日
        LocalDateTime localDateTime1 = localDateTime.withDayOfMonth(10).withYear(2020);
        System.out.println(localDateTime1);

        //手动创建日期
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println(date3);//2014-12-12
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println(date4);//22:15
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println(date5);//20:15:30
    }

    /***
     * 方法引用
     */
    private static void test4() {
        //1.静态方法引用
        FunInterface funInterface = (item) -> {
            return Test.getStaticMethod(item);
        };
        //简化
        funInterface.work("aa");
        //Test中的静态方法getStaticMetho传入的参数和返回的值与FunInterface接口中work方法一直
        FunInterface funInterface1 = Test::getStaticMethod;
        funInterface1.work("aa");

        //2.对象方法引用
        Function<String, Integer> function2 = String::length;
        System.out.println(function2.apply("对象方法"));

        //3.实例方法引用
        UserEntity userEntity = new UserEntity();
        FunInterface funInterface2 = userEntity::test;
        funInterface2.work("aa");

        //5.构造方法引用
        UserInterface userInterface = UserEntity::new;
        userInterface.getUser();
    }

    /***
     * 高级集合类及收集器
     */
    private static void test3() {
        //将一个集合分成两个集合
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("name", "wangmh1");
        list.add(map);
        Map map1 = new HashMap();
        map1.put("name", "wangmh2");
        list.add(map1);
        Map<Boolean, List<Map>> collect = list.stream().collect(Collectors.partitioningBy(item -> item.get("name").equals("wangmh1")));
        System.out.println("将一个集合分成两个集合");
        collect.forEach((index, item) -> System.out.println(index + "-" + item));

        //数据分组
        Map<Object, List<Map>> name = list.stream().collect(Collectors.groupingBy(item -> item.get("name")));
        System.out.println("数据分组");
        name.forEach((index, item) -> System.out.println(index + "-" + item));

        //字符串拼接
        String mame = list.stream().map(item -> item.get("name").toString()).collect(Collectors.joining(","));
        System.out.println("字符串拼接");
        System.out.println(mame);
    }

    /***
     * Stream流
     */
    private static void test2() {
        //2. map filter collect  flatMap max min count reduce
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("name", "wangmh1");
        list.add(map);
        Map map1 = new HashMap();
        map1.put("name", "wangmh2");
        list.add(map1);
        List<Object> name = list.stream().map(item -> item.get("name")).filter(item -> !item.equals("")).collect(Collectors.toList());

        List<Map> list1 = new ArrayList<>();
        Map map2 = new HashMap();
        map2.put("name", "wangmh1");
        list1.add(map2);
        Map map3 = new HashMap();
        map3.put("name", "wangmh2");
        list1.add(map3);
        //flatMap 调用Stream.of的静态方法将两个list转换为Stream，再通过flatMap将两个流合并为一个。
        List<Map> collect = Stream.of(list, list1).flatMap(item -> item.stream()).collect(Collectors.toList());

        //max min count
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(10);
        Optional<Integer> max = integers.stream().max(Comparator.comparing(item -> item));
        Optional<Integer> min = integers.stream().min(Comparator.comparing(item -> item));
        if (max.isPresent()) {//判断是否有值
            System.out.println(max.get());
        }
        if (min.isPresent()) {//判断是否有值
            System.out.println(min.get());
        }
        long count = integers.stream().count();

        //reduce 操作一组值，让其生成一个值
        Integer reduce = integers.stream().reduce(1, (x, y) -> x + y);
        System.out.println("reduce--->" + reduce);
    }


    /***
     * 自定义函数接口
     * @param funInterface
     */
    public static void test(FunInterface funInterface) {
        String work = funInterface.work("aa");
        System.out.println(work);
    }

}
