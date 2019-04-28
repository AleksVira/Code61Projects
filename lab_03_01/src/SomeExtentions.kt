import java.text.SimpleDateFormat
import java.util.*

fun <T> List<T>.toMapConverter(): Map<Int, T> {
    return associateBy { indexOf(it) }
}

fun Date.myFormattedString(): String {
    val myStringFormat = SimpleDateFormat("dd.mm.yyyy")
    return myStringFormat.format(this)
}

fun HashMap<String, String>.allKeys(): List<String> {
    return this.keys.toList()
}