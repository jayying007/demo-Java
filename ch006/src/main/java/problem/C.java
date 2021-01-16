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
public class C {
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
     * @problem 找出2011年所有交易 并按照交易额排序（低->高）
     */
    public List<Transaction> solve() {
        return transactions.stream()
                .filter(f -> f.getYear() == 2011)
                .sorted((e1, e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                .collect(Collectors.toList());
    }
}
