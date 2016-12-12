package us.jimschubert.kopper

/**
 * Splits a string on the first occurrence of a separator and returns a pair of key -> value?
 */
fun String.kvp(separator: Char = '='): Pair<String,String?> {
    val idx = indexOfFirst { it == separator }
    if(idx > 1) {
        val last = idx+1
        return Pair(substring(0, idx), if(last==length) null else substring(last, length))
    }

    return Pair(this, null)
}
