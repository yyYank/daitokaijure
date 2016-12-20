package daitokaijure.compiler

import java.io.File
import java.net.URL

/**
 * Created by yy_yank on 2016/12/20.
 */
class Classpath {
    private val classpath = arrayListOf<URL>()

    fun add(paths: String) {
        for (path in paths.split(File.pathSeparator)) {
            classpath.add(File(path).absoluteFile.toURI().toURL())
        }
    }

    fun getURLs(): Array<URL> {
        return classpath.toTypedArray()
    }
}