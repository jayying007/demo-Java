package problem;

import pojo.Trader;
import pojo.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhuangJieYing
 * @date 2021/1/12
 */
public class E {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");
    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    /**
     * @problem 查找出来自剑桥的交易员，并按姓名排序
     */
    public List<Trader> solve() {
        return transactions.stream()
                .map(e -> e.getTrader())
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted((t1,t2) -> t1.getName().compareTo(t2.getName()))
                .collect(Collectors.toList());
    }
}
