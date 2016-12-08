package kopper.cli

import kopper.*

fun main(args: Array<String>) {
    val parser = Parser()
    parser.name = "Kopper CLI"
    parser.applicationDescription = "Kopper example application"

    parser.flag("q", listOf("quiet", "silent"), description = "Run silently")
    parser.option("f", listOf("file"), description = "File name")
    parser.flag("a", listOf("allowEmpty"))
    parser.flag("h", listOf("help"), description = "Displays this message")

    parser.parse(arrayOf("-f", "asdf.txt", "--quiet=true", "trailing", "arguments" ))

    if(parser.isSet("h")) {
        println(parser.printHelp())
        return
    }

    println("q=${parser.isSet("q")}")
    println("quiet=${parser.isSet("quiet")}")
    println("silent=${parser.isSet("silent")}")
    println("f=${parser.get("f")}")
    println("file=${parser.get("file")}")
    println("allowEmpty=${parser.isSet("allowEmpty")}")
    println("remainingArgs=${parser.remainingArgs.joinToString()}")
}