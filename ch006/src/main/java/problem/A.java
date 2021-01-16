package problem;

import java.util.stream.IntStream;

/**
 * @author ZhuangJieYing
 * @date 2021/1/12
 */
public class A {
    int[] arr = {5, 3, 6, 10, 2, 7};

    /**
     * @problem 看到上面的arr数组了吗，输出数组每个数字的平方
     */
    public int[] solve() {
        return IntStream.of(arr)
                .map(e -> e * e)
                .toArray();
    }
}
