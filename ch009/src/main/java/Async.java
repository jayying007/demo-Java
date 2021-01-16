import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Async {
    public static void main(String[] args) {
        System.out.println("main函数开始执行");
        ExecutorService executor = Executors.newFixedThreadPool(1);

        CompletableFuture.supplyAsync(() -> {
            System.out.println("===task start===");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("===task finish===");
            return 3;
        }, executor).thenAcceptAsync(integer -> System.out.println("返回值" + integer));
        System.out.println("main函数执行结束");
    }
}
