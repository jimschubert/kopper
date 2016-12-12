package us.jimschubert.kopper

/**
 * Represents a collection of arguments provided by the user.
 *
 * Includes helper methods for querying by option name (short or long).
 *
 * @note Maintains a bucket of unparsed arguments
 */
class ArgumentCollection(val unparsedArgs: List<String>,
                         parsedArgumentList: List<Argument<*>>
) : List<Argument<*>> by parsedArgumentList {
    fun option(name: String): String? {
        val found: Argument<*>? = find { (it.option.shortOption == name || it.option.longOption.contains(name)) }
        return (found?.value as? String?)
    }

    fun flag(name: String): Boolean {
        val found = find {
            it.option.isFlag && (it.option.shortOption == name || it.option.longOption.contains(name))
        } ?: return false

        return found.value as? Boolean ?: false
    }
}