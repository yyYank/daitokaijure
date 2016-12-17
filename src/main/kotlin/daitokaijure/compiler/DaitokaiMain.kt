package daitokaijure.compiler

/**
 * Created by yy_yank on 2016/12/16.
 */
fun main(args: Array<String>) {

    // よくある四則演算
    println(DaitokaiParser.CALCULATOR.parse("3 * 3"))
    println(DaitokaiParser.CALCULATOR.parse("3 + 3"))
    println(DaitokaiParser.CALCULATOR.parse("3 - 3"))
    println(DaitokaiParser.CALCULATOR.parse("3 / 3"))


    // 大都会パーザ
    println(DaitokaiParser.DAITOKAI_CALCULATOR.parse("\"pen\" + \"apple\""))
    println(DaitokaiParser.DAITOKAI_CALCULATOR.parse("\"pen\" ppap \"apple\""))
    println(DaitokaiParser.DAITOKAI_CALCULATOR.parse("\"pen\" daitokai \"apple\""))



    // stripMargin的なもの
    println(
            DaitokaiParser.DAITOKAI_CALCULATOR.parse("""
                "a" + "b"
            """)
    )

    println(
            DaitokaiParser.DAITOKAI_CALCULATOR.parse("""
                "a" ppap "b"
            """)
    )

    println(
            DaitokaiParser.DAITOKAI_CALCULATOR.parse("""
                "a" daitokai "b"
            """)
    )

    println(
            DaitokaiParser.DAITOKAI_CALCULATOR.parse("""
                "b" daitokai "d"
            """)
    )

}