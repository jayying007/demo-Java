import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Client {
    public static void main(String[] args) {
        Method[] methods = Person.class.getDeclaredMethods();//不会获取父类方法，除非重写
        for(Method method : methods){
            System.out.println("定义的方法："+method.getName());
        }
        try {
            Class<?> personClass = Class.forName("Person");
            Person person = (Person)personClass.getDeclaredConstructor(String.class, int.class).newInstance("Jane", 22);
            Method method1 = personClass.getDeclaredMethod("introduction");
            method1.invoke(person);

            Method method2 = personClass.getDeclaredMethod("calAdd", int.class, int.class);
            int ans = (int)method2.invoke(person, 4, 7);
            System.out.printf("计算结果：%d\n", ans);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

    }
}
