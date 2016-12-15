package daitokaijure.compiler.frontend

import org.jparsec.*
import org.jparsec.Parser.Reference
import org.jparsec.pattern.Patterns
import java.util.function.BinaryOperator

/**
 * Created by yy_yank on 2016/12/14.
 */

fun main(args : Array<String>) {
//    val parser : Parser<Double> = ParserHolder.doubleParser
//     パーサを実行する。
//    val src = "(3 *3 + 11)* -10"
//    val result = parser.parse(src)
//    println(src + " = " + result) // expected:-2
}

object ParserHolder {

    val WHITESPACE_MUL = term("+", "-", "*", "/").not().retn(Any())
    private val OPERATORS = Terminals.operators("+", "-", "*", "/", "(", ")")
    val digits = Patterns.range('0', '9').many1()
    val fractional = Patterns.isChar('.').next(digits)

//    fun calculator(atom: Parser<Double>): Parser<Double> {
//        val reference = Parser.newReference<Double>()
//        val unit = reference.lazy().between(term("("), term(")")).or(atom)
//        val parser = OperatorTable<Double>()//.infixl(op("+") { l, r -> l + r }, 10).infixl(op("-") { l, r -> l - r }, 10).infixl(op("*") { l, r -> l * r }.or(WHITESPACE_MUL), 20).infixl(op("/") { l, r -> l / r }, 20).prefix(op("-") { v -> -v }, 30).build(unit)
////        reference.set(parser)
//        return parser
//    }

    fun <T> op(name: String, value: T): Parser<T> {
        return term(name).retn(value)
    }

    fun term(vararg names: String): Parser<*> {
//        return OPERATORS.token(names)
    }
}


