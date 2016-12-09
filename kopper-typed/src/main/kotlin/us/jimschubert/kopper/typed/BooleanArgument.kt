package us.jimschubert.kopper.typed

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class BooleanArgument(
        caller: TypedArgumentParser,
        val shortOption: String,
        val default: Boolean = true,
        val longOption: List<String> = listOf(),
        val description: String? = null
): ReadOnlyProperty<TypedArgumentParser, Boolean> {
    init {
        caller.parser.flag(shortOption, longOption, description, default)
    }

    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    override fun getValue(thisRef: TypedArgumentParser, property: KProperty<*>): Boolean {
        thisRef.ensureParsed()
        return thisRef.parser.isSet(shortOption)
    }
}