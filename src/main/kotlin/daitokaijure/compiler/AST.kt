package daitokaijure.compiler

import daitokaijure.core.type.DjAny
import daitokaijure.core.type.DjInt
import daitokaijure.core.type.DjString
import daitokaijure.core.type.DjType

/**
 * Created by yy_yank on 2016/12/13.
 */

interface AST
data class DjNil(val nothing : Nothing) : AST
data class DjT(val nothing : Nothing) : AST
data class DjCons(val car: AST, val cdr: AST) : AST
data class DjInt(val i: Int) : AST
data class DjSymbol(val s: String) : AST

