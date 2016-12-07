package kopper.typed

import kopper.Parser

abstract class TypedArgumentParser(val args: Array<String>) {
    private var hasParsed = false
    internal val self: TypedArgumentParser by lazy { this }
    internal val parser = Parser()
    internal fun ensureParsed() {
        if(!hasParsed) {
            parser.parse(args)
            hasParsed = true
        }
    }
}