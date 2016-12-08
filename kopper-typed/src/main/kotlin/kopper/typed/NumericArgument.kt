package kopper.typed

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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
        thisRef.ensureParsed()
        when (default) {
            is Float -> return thisRef.parser.get(shortOption)?.toFloat() as T? ?: default
            is Long -> return thisRef.parser.get(shortOption)?.toLong() as T? ?: default
            is Int -> return thisRef.parser.get(shortOption)?.toInt() as T? ?: default
            is Double -> return thisRef.parser.get(shortOption)?.toDouble() as T? ?: default
            is Byte -> return thisRef.parser.get(shortOption)?.toByte() as T? ?: default
            is Short -> return thisRef.parser.get(shortOption)?.toShort() as T? ?: default
        }

        return null
    }
}