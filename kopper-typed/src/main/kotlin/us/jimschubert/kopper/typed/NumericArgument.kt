package us.jimschubert.kopper.typed

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Delegates argument parsing of a numeric option defined by @param[T]
 */
class NumericArgument<out T>(
        caller: TypedArgumentParser,
        val shortOption: String,
        val default: T? = null,
        val longOption: List<String> = listOf(),
        val description: String? = null
): ReadOnlyProperty<TypedArgumentParser, T?> {

    init {
        caller.parser.option(shortOption, longOption, description, default = null)
    }

    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: TypedArgumentParser, property: KProperty<*>): T? {
        when (default) {
            is Float -> return thisRef.ensureParsed().option(shortOption)?.toFloat() as T? ?: default
            is Long -> return thisRef.ensureParsed().option(shortOption)?.toLong() as T? ?: default
            is Int -> return thisRef.ensureParsed().option(shortOption)?.toInt() as T? ?: default
            is Double -> return thisRef.ensureParsed().option(shortOption)?.toDouble() as T? ?: default
            is Byte -> return thisRef.ensureParsed().option(shortOption)?.toByte() as T? ?: default
            is Short -> return thisRef.ensureParsed().option(shortOption)?.toShort() as T? ?: default
        }

        return null
    }
}