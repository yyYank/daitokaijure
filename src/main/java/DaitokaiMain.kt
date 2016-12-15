/**
 * Created by yy_yank on 2016/12/16.
 */
fun main(args : Array<String>) {
    println(
            DaitokaiParser.STRING_CALCULATOR.parse("""
                "a" + "b"
            """)
    )

    println(
            DaitokaiParser.STRING_CALCULATOR.parse("""
                "a" ppap "b"
            """)
    )

    println(
            DaitokaiParser.STRING_CALCULATOR.parse("""
                "a" daitokai "b"
            """)
    )
}