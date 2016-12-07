# Kopper

A simple Kotlin option parser

# Example

Manual parsing…

```kotlin
package kopper.cli

import kopper.*

fun main(args: Array<String>) {
    val parser = Parser()
    parser.name = "Kopper CLI"
    parser.applicationDescription = "Kopper example application"

    parser.flag("q", listOf("quiet", "silent"), description = "Run silently")
    parser.option("f", listOf("file"), description = "File name")
    parser.flag("a", listOf("allowEmpty"))

    println(parser.printHelp())

    parser.parse(arrayOf("-f", "asdf.txt", "--quiet=true", "trailing", "arguments" ))

    println("q=${parser.isSet("q")}")
    println("quiet=${parser.isSet("quiet")}")
    println("silent=${parser.isSet("silent")}")
    println("f=${parser.get("f")}")
    println("file=${parser.get("file")}")
    println("allowEmpty=${parser.isSet("allowEmpty")}")
    println("remainingArgs=${parser.remainingArgs.joinToString()}")
}
```

Parser objects…

```kotlin
class ExampleArgs(args: Array<String>) : TypedArgumentParser(args) {

    val quiet by BooleanArgument(self, "q",
            default = true,
            longOption = listOf("quiet", "silent"),
            description = "Run quietly"
    )

    val rate by NumericArgument(self, "r", default = 1.0f, description = "Rate")

    val maxCount by NumericArgument(self, "m", longOption = listOf("maxCount"), default = 10, description = "Max Count")
}
```

# License

[MIT License](./LICENSE)
