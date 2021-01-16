import java.util.Random;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Person {
    int id;
    String name;
    int age;
    Person(){

    }
    Person(String name, int age){
        this.name = name;
        this.age = age;
        //generate a random id
        Random random = new Random();
        this.id = random.nextInt(100);
    }
    Person(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public void introduction(){
        System.out.printf("Id:%d - Hello,my name is %s,and I'm %d years old.\n", id, name, age);
    }

    public int calAdd(int a, int b){
        return a+b;
    }

}
