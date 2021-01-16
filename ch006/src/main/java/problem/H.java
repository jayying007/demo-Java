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
public class H {
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
     * @problem 所有交易中，最高的交易额是多少
     */
    public int solve() throws Exception {
        if(transactions == null || transactions.isEmpty()) {
            throw new Exception("can not find any transaction");
        }
        Optional<Integer> optional = transactions.stream()
                .map(e -> e.getValue())
                .max((v1, v2) -> Integer.compare(v1, v2));
        return optional.get();
    }
}
