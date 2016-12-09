package us.jimschubert.kopper.typed

import us.jimschubert.kopper.Parser

abstract class TypedArgumentParser(val args: Array<String>) {
    private var hasParsed = false
    val self: TypedArgumentParser by lazy { this }
    internal val parser = Parser()
    internal fun ensureParsed() {
        if(!hasParsed) {
            parser.parse(args)
            hasParsed = true
        }
    }

    fun printHelp() : String {
        return parser.printHelp()
    }

    val _etc_: List<String> get() = parser.remainingArgs
}