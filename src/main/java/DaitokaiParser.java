import org.jparsec.OperatorTable;
import org.jparsec.Parser;
import org.jparsec.Terminals;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * Created by yy_yank on 2016/12/15.
 */
public class DaitokaiParser {

    private static final Terminals OPERATORS =
            Terminals.operators("+", "-", "*", "/", "(", ")");

    static final Parser<BinaryOperator> WHITESPACE_MUL =
            term("+", "-", "*", "/").not().retn(BinaryOperator.maxBy(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return 0;
                }
            }));


    static Parser<?> term(String... names) {
        return OPERATORS.token(names);
    }

    static <T> Parser<T> op(String name, T value) {
        return term(name).retn(value);
    }

    static Parser<Double> calculator(Parser<Double> atom) {
        Parser.Reference<Double> ref = Parser.newReference();
        Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
        Parser<Double> parser = new OperatorTable<Double>()
                .infixl(op("+", (l, r) -> l + r), 10)
                .infixl(op("-", (l, r) -> l - r), 10)
//                .infixl(op("*", (l, r) -> l * r).or(WHITESPACE_MUL), 20)
                .infixl(op("/", (l, r) -> l / r), 20)
                .prefix(op("-", v -> -v), 30)
                .build(unit);
        ref.set(parser);
        return parser;
    }
}
