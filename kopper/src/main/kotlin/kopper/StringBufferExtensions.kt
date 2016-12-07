package kopper

fun Appendable.tab(): Appendable {
    append('\t')
    return this
}