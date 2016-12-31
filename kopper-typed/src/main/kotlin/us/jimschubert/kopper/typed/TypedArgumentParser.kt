package us.jimschubert.kopper.typed

import us.jimschubert.kopper.ArgumentCollection
import us.jimschubert.kopper.Parser
import java.io.PrintStream

/**
 * A base type for object-oriented argument parsing.
 *
 * Use delegated properties defined by, e.g. [BooleanArgument], [NumericArgument], [StringArgument]
 */
abstract class TypedArgumentParser(val args: Array<String>, val name: String? = null, val applicationDescription: String? = null) {
    private var arguments: ArgumentCollection? = null
    val self: TypedArgumentParser by lazy { this }
    internal val parser = Parser()

    init {
        if(name != null) parser.setName(name)
        if(applicationDescription != null) parser.setApplicationDescription(applicationDescription)
    }

    internal fun ensureParsed() : ArgumentCollection {
        if(arguments == null) {
            arguments = parser.parse(args)
        }
        return arguments!!
    }

    fun printHelp() : String = parser.printHelp()

    fun printHelp(out: PrintStream) = parser.printHelp(out)

    val _etc_: List<String> get() {
        return ensureParsed().unparsedArgs
    }
}