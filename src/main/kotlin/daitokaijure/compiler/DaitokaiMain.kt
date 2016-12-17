package daitokaijure.compiler

import daitokaijure.compiler.DaitokaiParserK

/**
 * Created by yy_yank on 2016/12/16.
 */
fun main(args : Array<String>) {
    println(
            DaitokaiParserK.STRING_CALCULATOR.parse("""
                "a" + "b"
            """)
    )

    println(
            DaitokaiParserK.STRING_CALCULATOR.parse("""
                "a" ppap "b"
            """)
    )

    println(
            DaitokaiParserK.STRING_CALCULATOR.parse("""
                "a" daitokai "b"
            """)
    )

    println(
            DaitokaiParserK.STRING_CALCULATOR.parse("""
                "b" daitokai "d"
            """)
    )
}