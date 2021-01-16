package problem;

import pojo.Trader;
import pojo.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class F {
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
     * @problem 打印生活在剑桥的交易员的所有交易额
     */
    public List<Integer> solve() {
        return transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .map(t -> t.getValue())
                .collect(Collectors.toList());
    }
}
