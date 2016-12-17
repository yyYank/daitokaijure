package daitokaijure.compiler

import org.jparsec.*
import java.util.function.BiFunction as bifunc
import java.util.function.Function as func

/**
 * Created by yy_yank on 2016/12/17.
 */
object DaitokaiParser {
    private val OPERATORS = Terminals.operators("+", "-", "*", "/", "(", ")", "ppap", "daitokai", "via")

    internal val STRING = Terminals.StringLiteral.PARSER
            .map<String>(func<String, String> (
                            java.lang.String::valueOf
            ))
    internal val NUMBER = Terminals.DecimalLiteral.PARSER
            .map<Double>(func<String, Double>(
                    java.lang.Double::valueOf
            ))
    // 字句解析器
    internal val TOKENIZER: Parser<*> = Parsers.or(
            Terminals.DecimalLiteral.TOKENIZER,
            OPERATORS.tokenizer()
    )

    // 字句解析器
    internal val STRING_TOKENIZER: Parser<*> = Parsers.or(
            Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER,
            OPERATORS.tokenizer()
    )

    // パーザが許容する（無視するもの）
    internal val IGNORED = Parsers.or(
            Scanners.JAVA_LINE_COMMENT,
            Scanners.JAVA_BLOCK_COMMENT,
            Scanners.WHITESPACES
    ).skipMany()

    val CALCULATOR = calculator(NUMBER).from(TOKENIZER, IGNORED)
    val DAITOKAI_CALCULATOR = daitokaiCalculator(STRING).from(STRING_TOKENIZER, IGNORED)

    internal fun term(vararg names: String): Parser<*> {
        return OPERATORS.token(*names)
    }

    internal fun <T> op(name: String, value: T): Parser<T> {
        return term(name).retn(value)
    }


    // パーザ定義
    internal fun calculator(atom: Parser<Double>): Parser<Double> {
        val ref = Parser.newReference<Double>()
        val unit = ref.lazy().between(term("("), term(")")).or(atom)
        val parser = OperatorTable<Double>()
                .infixl(op<bifunc<in Double, in Double, out Double>>("+", bifunc { l, r -> l + r }), 10)
                .infixl(op<bifunc<in Double, in Double, out Double>>("-", bifunc { l, r -> l - r }), 10)
                .infixl(op<bifunc<in Double, in Double, out Double>>("*", bifunc { l, r -> l * r }), 10)
                .infixl(op<bifunc<in Double, in Double, out Double>>("/", bifunc { l, r -> l / r }), 20)
                .prefix(op<func<in Double, out Double>>("-", func { v -> -v }), 30)
                .infixl(op<bifunc<in Double, in Double, out Double>>("ppap", bifunc { l, r -> l / 100 }), 20)
                .build(unit)
        ref.set(parser)
        return parser
    }

    // パーザー定義
    internal fun daitokaiCalculator(atom: Parser<String>): Parser<String> {
        val ref = Parser.newReference<String>()
        val unit = ref.lazy().between(term("("), term(")")).or(atom)
        val parser = OperatorTable<String>()
                .infixl(op<bifunc<in String, in String, out String>>("+", bifunc { l, r -> l + r }), 10)
                .infixl(op<bifunc<in String, in String, out String>>("ppap", bifunc { l, r -> "applepen" }), 20)
                .infixl(op<bifunc<in String, in String, out String>>("daitokai", bifunc { l, r -> "アクティブ体操" }), 20)
                .infixl(op<bifunc<in String, in String, out String>>("via", bifunc { l, r -> if(l == "岡山" && r == "服部" ){ "桃太郎線" } else { "知らんけど山陽本線か宇野線か津山線か吉備線か赤穂線か伯備線かじゃないかなぁ" } }), 20)
                .build(unit)
        ref.set(parser)
        return parser
    }




}

