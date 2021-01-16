import java.util.concurrent.TimeUnit;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Example {
    private final ThreadLocal<String> local3 = new ThreadLocal<>();
    public static void main(String[] args) {
        Example example = new Example();
        example.local2();
        System.out.println("local1,local2已失效");
    }
    public void local2() {
        ThreadLocal<String> local1 = new ThreadLocal<>();
        ThreadLocal<String> local2 = new ThreadLocal<>();
        Thread thread1 = new Thread(() -> {
            local1.set("value1");
            local2.set("value2");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread1 var1:" + local1.get());
            System.out.println("Thread1 var2:" + local2.get());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            local3.set("value3");
            System.out.println("Thread1 var3:" + local3.get());
        });
        Thread thread2 = new Thread(() -> {
            local1.set("value4");
            local2.set("value5");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread2 var1:" + local1.get());
            System.out.println("Thread2 var2:" + local2.get());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            local3.set("value6");
            System.out.println("Thread2 var3:" + local3.get());
        });
        thread1.start();
        thread2.start();
    }

}