package kopper

abstract class Option<T>(
        open val shortOption: String,
        open val longOption: List<String> = listOf(),
        open val description: String? = null,
        open val default: T? = null,
        open val parameterText: String? = null,
        val isFlag: Boolean = false) {

    internal var actual: T? = null

    val help: String
        get() {
            val buffer = StringBuffer()
            buffer.tab().append("-$shortOption")
            if(!isFlag) {
                buffer.append(" ")
                buffer.append(parameterText?:"value")
            }

            longOption.forEach { s: String ->
                buffer.append(", --$s")
                if(!isFlag) {
                    buffer.append("=")
                    buffer.append(parameterText?:"value")
                }
            }

            if(description != null) {
                buffer.appendln()
                        .tab()
                        .tab()
                        .append(description)
            }

            buffer.appendln()

            return buffer.toString()
        }

    @Suppress("UNCHECKED_CAST")
    open fun parseOption(value: String?): T? {
        return (value as? T?) ?: default
    }

    internal fun applyParsedOption(value: String?): Unit {
        actual = parseOption(value)
    }
}

class StringOption(
        override val shortOption: String,
        override val longOption: List<String> = listOf(),
        override val description: String? = null,
        override val default: String? = null,
        override val parameterText: String? = null) : Option<String>(
        shortOption,
        longOption,
        description,
        default,
        parameterText,
        false
)

class BooleanOption(
        override val shortOption: String,
        override val longOption: List<String> = listOf(),
        override val description: String? = null,
        override val default: Boolean? = true,
        override val parameterText: String? = null) : Option<Boolean>(
        shortOption,
        longOption,
        description,
        default,
        parameterText,
        true
) {
    override fun parseOption(value: String?): Boolean? {
        return value?.toBoolean() ?: default
    }
}
