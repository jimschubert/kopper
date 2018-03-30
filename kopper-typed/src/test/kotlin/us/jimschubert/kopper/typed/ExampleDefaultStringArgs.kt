package us.jimschubert.kopper.typed

class ExampleDefaultStringArgs(args: Array<String>) : TypedArgumentParser(args) {
    val first: String by StringArgument(self, "f", default = "first")
    val second by StringArgument(self, "s", default = "")
    val third by StringArgument(self, "t")
}