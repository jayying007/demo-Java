import factory.Factory;
import pojo.Person;

/**
 * @author jane
 * @date  2020/5/6
 * @description
 */
public class Client {
    public static void main(String[] args) {
        Factory factory = new Factory("ch008/src/main/resources/config.xml");
        Person person = (Person)factory.get("Person");
        System.out.println(person.getCat());
    }
}