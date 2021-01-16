package com;

import java.lang.reflect.InvocationTargetException;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader loader1 = new MyClassLoader("/home/jayying/tmp/");
        //设置双亲加载为null，说明由自己加载
        MyClassLoader loader2 = new MyClassLoader(null, "/home/jayying/tmp/");

        //先被AppClassLoader加载到了
        Class<?> cat1 = loader1.loadClass("com.Cat");
        //取消了双亲委派，由自己加载
        Class<?> cat2 = loader2.loadClass("com.Cat");

        try {
            cat1.getDeclaredConstructor(String.class).newInstance("Tom");
            cat2.getDeclaredConstructor(String.class).newInstance("Kitty");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
