package daitokaijure.compiler.frontend

import org.jparsec.Parser
import org.jparsec.Parser.Reference
import org.jparsec.Scanners
import org.jparsec.pattern.Patterns

/**
 * Created by yy_yank on 2016/12/14.
 */

fun main(args : Array<String>) {
    val parser : Parser<Double> = ParserHolder.doubleParser
    // パーサを実行する。
    val src = "(3 *3 + 11)* -10"
    val result = parser.parse(src)
    println(src + " = " + result) // expected:-2
}

object ParserHolder {

    val digits = Patterns.range('0', '9').many1()
    val fractional = Patterns.isChar('.').next(digits)
    val number = digits.next(fractional.optional())
    val scannerNumber = Scanners.INTEGER
    val l_number : Parser<Tok> = Lexers.lexer(s_number, Tokenizers.forDecimal());

    val doubleParser = Reference<Double>().lazy()
    init {
        doubleParser.
    }
}


