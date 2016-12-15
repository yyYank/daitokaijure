import org.jparsec.*;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * Created by yy_yank on 2016/12/15.
 */
public class DaitokaiParser {

    private static final Terminals OPERATORS =
            Terminals.operators("+", "-", "*", "/", "(", ")", "ppap", "daitokai");

    static final Parser<String> STRING =
            Terminals.StringLiteral.PARSER.map(String::valueOf);

    static final Parser<Double> NUMBER =
            Terminals.DecimalLiteral.PARSER.map(Double::valueOf);

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
                .infixl(op("*", (l, r) -> l * r), 10)
                .infixl(op("/", (l, r) -> l / r), 20)
                .prefix(op("-", v -> -v), 30)
                .infixl(op("ppap", (l, r) -> l / 100), 20)
                .build(unit);
        ref.set(parser);
        return parser;
    }

    static Parser<String> s_calculator(Parser<String> atom) {
        Parser.Reference<String> ref = Parser.newReference();
        Parser<String> unit = ref.lazy().between(term("("), term(")")).or(atom);
        Parser<String> parser = new OperatorTable<String>()
                .infixl(op("+", (l, r) -> l + r), 10)
                .infixl(op("ppap", (l, r) -> "applepen"), 20)
                .infixl(op("daitokai", (l, r) -> "アクティブ体操"), 20)
                .build(unit);
        ref.set(parser);
        return parser;
    }

    static final Parser<?> TOKENIZER =
            Parsers.or(Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());

    static final Parser<?> STRING_TOKENIZER =
            Parsers.or(Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER, OPERATORS.tokenizer());

    static final Parser<Void> IGNORED = Parsers.or(
            Scanners.JAVA_LINE_COMMENT,
            Scanners.JAVA_BLOCK_COMMENT,
            Scanners.WHITESPACES).skipMany();

    public static final Parser<Double> CALCULATOR =
            calculator(NUMBER).from(TOKENIZER, IGNORED);

    public static final Parser<String> STRING_CALCULATOR =
            s_calculator(STRING).from(STRING_TOKENIZER, IGNORED);

    public static void main(String[] args) {
        System.out.println(CALCULATOR.parse("3 * 3"));
        System.out.println(CALCULATOR.parse("3 + 3"));
        System.out.println(CALCULATOR.parse("3 - 3"));
        System.out.println(CALCULATOR.parse("3 / 3"));
        System.out.println(STRING_CALCULATOR.parse("\"pen\" + \"apple\""));
        System.out.println(STRING_CALCULATOR.parse("\"pen\" ppap \"apple\""));
        System.out.println(STRING_CALCULATOR.parse("\"pen\" daitokai \"apple\""));
    }
}
