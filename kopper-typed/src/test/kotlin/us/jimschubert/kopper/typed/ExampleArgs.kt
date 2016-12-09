package us.jimschubert.kopper.typed

class ExampleArgs(args: Array<String>) : TypedArgumentParser(args) {

    val quiet by BooleanArgument(self, "q",
            default = true,
            longOption = listOf("quiet", "silent"),
            description = "Run quietly"
    )

    val rate by NumericArgument(self, "r", default = 1.0f, description = "Rate")

    val maxCount by NumericArgument(self, "m", longOption = listOf("maxCount"), default = 10, description = "Max Count")
}