package us.jimschubert.kopper.cli

import us.jimschubert.kopper.typed.BooleanArgument
import us.jimschubert.kopper.typed.StringArgument
import us.jimschubert.kopper.typed.TypedArgumentParser

fun main(args: Array<String>) {
    val arguments = TypedArgs(
            arrayOf(
                    "-version",
                    "-f",
                    "asdf.txt",
                    "--allowEmpty",
                    "-q",
                    "trailing",
                    "arguments")
    )

    if (arguments.help) return arguments.printHelp(System.out)

    println("quiet=${arguments.quiet}")
    println("file=${arguments.file}")
    println("allowEmpty=${arguments.allowEmpty}")
    println("remainingArgs=${arguments._etc_.joinToString()}")

    if (arguments.version) {
        println("version 1.0.0 or whatever.")
    }
}

class TypedArgs(args: Array<String>) : TypedArgumentParser(args, "TypedApp", "Example Typed Arguments Application") {
    val quiet by BooleanArgument(self, "q",
            default = true,
            longOption = listOf("quiet", "silent"),
            description = "Run quietly"
    )

    val file by StringArgument(self, "f",
            longOption = listOf("file"),
            description = "The filename to process",
            parameterText = "filename")

    val allowEmpty by BooleanArgument(self, "a",
            longOption = listOf("allowEmpty"))

    val help by BooleanArgument(self, "h",
            longOption = listOf("help"),
            description = "Display the help message")

    val version by BooleanArgument(self, "version", description = "Get the current version")
}