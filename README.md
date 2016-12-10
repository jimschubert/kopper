# Kopper

A simple Kotlin option parser

[![Maven Central](https://img.shields.io/maven-central/v/us.jimschubert/kopper.svg?label=maven:%20kopper)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kopper%22)

[![Maven Central](https://img.shields.io/maven-central/v/us.jimschubert/kopper-typed.svg?label=maven:%20kopper-typed)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kopper-typed%22)

`kopper` is the simple Kotlin option parser library.

`kopper-typed` extends the kopper library to support delegated properties and parsing as simple as constructing an object. It includes the additional dependency of `kotlin-reflect`.

# Examples

Manual parsing…

```kotlin
import us.jimschubert.kopper.*

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
fun main(args: Array<String>) {
    val arguments = ExampleArgs(args)

    if (arguments.help) {
        println(arguments.printHelp())
        return
    }

    println("quiet=${arguments.quiet}")
    println("rate=${arguments.rate}")
    println("maxCount=${arguments.maxCount}")
}

class ExampleArgs(args: Array<String>) : TypedArgumentParser(args) {

    val quiet by BooleanArgument(self, "q",
            default = true,
            longOption = listOf("quiet", "silent"),
            description = "Run quietly"
    )

    val rate by NumericArgument(self, "r", 
            default = 1.0f, 
            description = "Rate"
    )

    val maxCount by NumericArgument(self, "m", 
            longOption = listOf("maxCount"), 
            default = 10, 
            description = "Max Count"
    )
}
```

Typed parser objects support `BooleanArgument`, `StringArgument`, and `NumericArgument<T>` property delegates. They 
also expose any additional args as a list of strings in the `_etc_` property.

# License

[MIT License](./LICENSE)
