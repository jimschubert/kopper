package us.jimschubert.kopper

class Parser {
    private var options: MutableList<Option<*>> = mutableListOf()
    private var _args: MutableList<String> = mutableListOf()

    private var _name: String? = null
    val name: String?
        get() = _name

    private var _applicationDescription: String? = null
    val applicationDescription: String?
        get() = _applicationDescription

    val remainingArgs: List<String> get() = _args.toList()

    fun option(shortOption: String,
               longOption: List<String> = listOf(),
               description: String? = null,
               default: String? = null): Parser {
        options.add(StringOption(shortOption, longOption, description, default))

        return this
    }

    fun setName(name: String) : Parser {
        _name = name
        return this
    }

    fun setApplicationDescription(description: String) : Parser {
        _applicationDescription = description
        return this
    }

    fun flag(shortOption: String,
               longOption: List<String> = listOf(),
               description: String? = null,
               default: Boolean? = true): Parser {
        options.add(BooleanOption(shortOption, longOption, description, default))

        return this
    }

    fun <T> custom(option: Option<T>): Parser {
        options.add(option)

        return this
    }

    fun parse(args: Array<String>) {
        var argIndex = 0
        while(argIndex < args.size) {
            val s = args[argIndex]

            if(s.startsWith("--")) {
                val (left,right) = s.removePrefix("--").kvp()
                val option = options.find { it.longOption.contains(left) }
                if(false == option?.isFlag)
                    option?.applyParsedOption(right)
                else
                    option?.applyParsedOption(right)
            } else if (s.startsWith("-")) {
                val next = if(argIndex < (args.size-1)) args[argIndex+1] else null
                val option = options.find { it.shortOption == s.removePrefix("-")}
                if(false == next?.startsWith("-")) {
                    option?.applyParsedOption(next)
                    argIndex++
                } else if (option != null) {
                    option?.setAsDefault()
                }
            } else _args.add(s)

            argIndex++
        }
    }

    fun get(option: String): String? {
        val found = options.find { (it.shortOption == option || it.longOption.contains(option)) }
        return found?.actual as? String?
    }

    fun isSet(option: String): Boolean {
        val found = options.find {
            it.isFlag && (it.shortOption == option || it.longOption.contains(option))
        } as? BooleanOption ?: return false

        return found.actual ?: false
    }

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