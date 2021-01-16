package problem;

import pojo.Trader;
import pojo.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class J {
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
     * @problem 计算总交易额
     */
    public int solve() {
        Optional<Integer> optional = transactions.stream()
                .map(e -> e.getValue())
                .reduce((v1,v2) -> v1 + v2);
        return optional.orElse(0);
    }
}