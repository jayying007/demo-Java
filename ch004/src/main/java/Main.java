import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author jayying
 * @date 2021/1/7
 */
public class Main {
    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        Random random = new Random();
        IntStream.range(0, 5).forEach(value -> new Thread(() -> {
            local.set(value + "  " + random.nextInt(1000) + "");
            //让线程睡眠，以便其他线程可以执行local.set()方法。
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程号和随机数为：" + local.get());
        }).start());
    }
}
