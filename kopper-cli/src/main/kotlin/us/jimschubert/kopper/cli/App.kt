package us.jimschubert.kopper.cli

import us.jimschubert.kopper.*

fun main(args: Array<String>) {
    val parser = Parser()
    parser.setName("Kopper CLI")
    parser.setApplicationDescription("Kopper example application")

    parser.flag("q", listOf("quiet", "silent"), description = "Run silently")
    parser.option("f", listOf("file"), description = "File name")
    parser.flag("a", listOf("allowEmpty"))
    parser.flag("h", listOf("help"), description = "Displays this message")

    val options = parser.parse(arrayOf("-f", "asdf.txt", "--quiet=true", "trailing", "arguments" ))

    if(options.flag("h")) return parser.printHelp(System.out)

    println("q=${options.flag("q")}")
    println("quiet=${options.flag("quiet")}")
    println("silent=${options.flag("silent")}")
    println("f=${options.option("f")}")
    println("file=${options.option("file")}")
    println("allowEmpty=${options.flag("allowEmpty")}")
    println("unparsedArgs=${options.unparsedArgs.joinToString()}")
}