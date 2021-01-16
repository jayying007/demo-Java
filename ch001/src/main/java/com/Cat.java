package com;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Cat {

    public Cat(String name){
        System.out.printf("Create a new Cat,named %s --> Power by %s\n", name, this.getClass().getClassLoader());
    }
}
