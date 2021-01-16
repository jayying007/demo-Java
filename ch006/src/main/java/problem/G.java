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
public class G {
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
     * @problem
     */
    public List<String> solve() {
        System.out.println("8.返回所有交易员姓名的字符串，并按字母顺序排序打印");
        return transactions.stream()
                .map(e -> e.getTrader().getName())
                .distinct()
                .sorted((s1,s2) -> s1.compareTo(s2))
                .collect(Collectors.toList());
    }
}
