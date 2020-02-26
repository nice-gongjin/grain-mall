package com.grain.mall.controller;

/**
 * @author gj
 * java创建对象的方式
 *  1.使用new关键字
 *  2.使用反射机制创建对象：
 *     (1)使用Class类的newInstance方法
 *     (2)java.lang.reflect.Constructor类里也有一个newInstance方
 *  3.使用clone方法：先实现Cloneable接口并实现其定义的clone方法
 *  4.使用反序列化
 */
public class test {
    public static void main(String[] args) {
        aaa aaa = new aaa();
        try {
            Class<?> aClass = Class.forName("com.grain.mall.controller.aaa");
            Object newInstance = aClass.newInstance();
            System.out.println(aaa);
            System.out.println(aaa.getClass());
            System.out.println(newInstance);
            System.out.println(newInstance.getClass());
            System.out.println(aaa == newInstance);
            System.out.println(aaa.getClass() == newInstance.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class aaa {
    public aaa() {}
}
