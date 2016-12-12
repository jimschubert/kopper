package us.jimschubert.kopper

/**
 * A generic representation of a command line option
 */
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

    internal fun applyParsedOption(value: String?): T? {
        actual = parseOption(value)
        return actual
    }

    internal fun setAsDefault(): T? {
        this.actual = this.default
        return this.actual
    }
}

/**
 * A command line option represented as a string
 */
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

/**
 * A command line option represented as a true/false value
 */
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
