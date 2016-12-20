package daitokaijure.compiler


import java.util.*

/**
 * Created by yy_yank on 2016/12/20.
 */
interface Runner {
    fun run(classPath : Classpath, args : ArrayList<String>)
}
