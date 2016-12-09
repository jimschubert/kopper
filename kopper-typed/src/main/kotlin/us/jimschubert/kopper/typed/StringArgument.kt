package us.jimschubert.kopper.typed

import us.jimschubert.kopper.StringOption
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class StringArgument(
        caller: TypedArgumentParser,
        val shortOption: String,
        default: String? = null,
        longOption: List<String> = listOf(),
        description: String? = null,
        parameterText: String? = null
): ReadOnlyProperty<TypedArgumentParser, String> {
    init {
        caller.parser.custom(StringOption(shortOption, longOption, description, default, parameterText))
    }

    /**
     * Returns the value of the property for the given object.
     * @param thisRef the object for which the value is requested.
     * @param property the metadata for the property.
     * @return the property value.
     */
    override fun getValue(thisRef: TypedArgumentParser, property: KProperty<*>): String {
        thisRef.ensureParsed()
        return thisRef.parser.get(shortOption) ?: ""
    }
}