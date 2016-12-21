package daitokaijure.compiler

import java.io.File

/**
 * Created by yy_yank on 2016/12/20.
 */
object Main{

    private val DAITOKAI_HOME: File

    init {
        val home = System.getProperty("daitokai.home")
        if (home == null) {
            System.err.println("error: no daitokai.home system property was passed")
            System.exit(1)
        }
        DAITOKAI_HOME = File(home)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val classpath = Classpath()
        var runner: Runner? = null
        var collectingArguments = false
        val arguments = arrayListOf<String>()
        classpath.add(".")
        var i = 0
        while (i < args.size) {
            val arg = args[i]
            if (collectingArguments) {
                arguments.add(arg)
                i++
                continue
            }

            fun next(): String {
                if (++i == args.size) {
                    throw RunnerException("argument expected to $arg")
                }
                return args[i]
            }

            if ("-help" == arg || "-h" == arg) {
                printUsageAndExit()
            }
            else if ("-version" == arg) {
                printVersionAndExit()
            }
            else if ("-classpath" == arg || "-cp" == arg) {
                classpath.add(next())
            }
            else if ("-expression" == arg || "-e" == arg) {
//                runner = ExpressionRunner(next())
                collectingArguments = true
            }
            else if (arg.startsWith("-")) {
                throw RunnerException("unsupported argument: $arg")
            }
            else if (arg.endsWith(".jar")) {
//                runner = JarRunner(arg)
                collectingArguments = true
            }
            else {
//                runner = MainClassRunner(arg)
                collectingArguments = true
            }
            i++
        }

        runner?.run(classpath, arguments)
    }

    private fun printVersionAndExit() {
    }

    private fun printUsageAndExit() {
    }
}