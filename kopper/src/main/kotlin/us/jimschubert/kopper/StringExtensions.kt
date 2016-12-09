package us.jimschubert.kopper

fun String.kvp(): Pair<String,String?> {
    val idx = indexOfFirst { it == '=' }
    if(idx > 1) {
        val last = idx+1
        return Pair(substring(0, idx), if(last==length) null else substring(last, length))
    }

    return Pair(this, null)
}
