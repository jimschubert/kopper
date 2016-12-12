package us.jimschubert.kopper

/**
 * Represents an actual value passed via command line arguments, including the option
 * responsible for parsing the argument, and the args list that provided options and values.
 */
data class Argument<out T>(val value: T?, val option: Option<*>, val args: List<String> = listOf())

/**
 * Merges a newer argument with an older argument
 */
operator fun <T> Argument<T>.plus(newer: Argument<T>): Argument<T> {
    return copy(value = newer.value, args = args + newer.args)
}