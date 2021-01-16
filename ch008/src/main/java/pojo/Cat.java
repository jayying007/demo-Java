package pojo;

/**
 * @author: jane
 * @CreateTime: 2020/5/6
 * @Description:
 */
public class Cat {
    String name;
    String hobby;

    public Cat(){

    }
    public Cat(String name, String hobby){
        this.name = name;
        this.hobby = hobby;
    }
    @Override
    public String toString() {
        return String.format("A cat named %s, likes %s", name, hobby);
    }
}
