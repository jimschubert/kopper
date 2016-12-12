package us.jimschubert.kopper

/**
 * Defines how a set of command line options will be parsed into the arguments that were passed
 */
class Parser {
    private var options: MutableList<Option<*>> = mutableListOf()
    private var _args: MutableList<String> = mutableListOf()

    private var _name: String? = null

    /**
     * The name of the application
     */
    val name: String?
        get() = _name

    private var _applicationDescription: String? = null

    /**
     * A description of the application
     */
    val applicationDescription: String?
        get() = _applicationDescription

    fun option(shortOption: String,
               longOption: List<String> = listOf(),
               description: String? = null,
               default: String? = null): Parser {
        options.add(StringOption(shortOption, longOption, description, default))

        return this
    }

    /**
     * Sets the application name
     */
    fun setName(name: String) : Parser {
        _name = name
        return this
    }

    /**
     * Sets the application description
     */
    fun setApplicationDescription(description: String) : Parser {
        _applicationDescription = description
        return this
    }

    /**
     * Defines a flag/switch represented as a true or false value
     */
    fun flag(shortOption: String,
               longOption: List<String> = listOf(),
               description: String? = null,
               default: Boolean? = true): Parser {
        options.add(BooleanOption(shortOption, longOption, description, default))

        return this
    }

    /**
     * Allows for defining custom options derived from [Option]
     */
    fun <T> custom(option: Option<T>): Parser {
        options.add(option)

        return this
    }

    /**
     * Parses input args into a collection of arguments with metadata about how those arguments were parsed.
     */
    fun parse(args: Array<String>): ArgumentCollection {
        _args.clear()
        val passedArguments : MutableList<Argument<*>> = mutableListOf()
        var argIndex = 0

        while(argIndex < args.size) {
            val s = args[argIndex]

            if(s.startsWith("--")) {
                val (left,right) = s.removePrefix("--").kvp()
                val option = options.find { it.longOption.contains(left) }
                val result = if(false == option?.isFlag) {
                    option?.applyParsedOption(right)
                }
                else {
                    option?.applyParsedOption(right)
                }

                if(option != null) {
                    val usedArgs = if (right != null) listOf(left, right) else listOf(left)
                    val prev = passedArguments.find { it.option == option }
                    val current = Argument(result, option, usedArgs)
                    if(prev != null) {
                        passedArguments.remove(prev)
                        passedArguments.add(prev + current)
                    } else {
                        passedArguments.add(current)
                    }
                }
            } else if (s.startsWith("-")) {
                val next = if(argIndex < (args.size-1)) args[argIndex+1] else null
                val option = options.find { it.shortOption == s.removePrefix("-")}
                val hasFollowingOption = next?.startsWith("-")
                val result = if(false == hasFollowingOption) {
                    argIndex++
                    option?.applyParsedOption(next)
                } else {
                    option?.setAsDefault()
                }

                if(option != null) {
                    val usedArgs = if(false == hasFollowingOption) listOf<String>(next!!) else listOf<String>()
                    val prev = passedArguments.find { it.option == option }
                    val current = Argument(result, option, usedArgs)
                    if(prev != null) {
                        passedArguments.remove(prev)
                        passedArguments.add(prev + current)
                    } else {
                        passedArguments.add(current)
                    }
                }
            } else _args.add(s)

            argIndex++
        }

        return ArgumentCollection(_args.toList(), passedArguments)
    }

    /**
     * Provides a formatted string for printing a help message to a user.
     */
    fun printHelp(): String {
        val buffer = StringBuffer()
        if(name != null) {
            buffer.appendln("NAME")
                    .tab()
                    .append(name)

            if(applicationDescription!=null){
                buffer.appendln(": $applicationDescription")
            }

            buffer.appendln()
        }

        if(options.isNotEmpty()) {
            buffer.appendln("OPTIONS")

            options.forEach { option ->
                buffer.appendln(option.help)
            }
        }

        return buffer.toString()
    }
}