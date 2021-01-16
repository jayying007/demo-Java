package pojo;


import annotation.Autowire;
import annotation.Component;

/**
 * @author: jane
 * @CreateTime: 2020/5/6
 * @Description:
 */
@Component
public class Person {
    @Autowire
    private Cat cat;

    public Cat getCat() {
        return cat;
    }
}
