import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Stream

val storeDir = File("inputs/")

fun input(day: Int, year: Int = 2020): String {
    val file = File(File(storeDir, "$year"), "$day.txt")
    file.parentFile.mkdirs()
    if(file.exists()) {
        return file.readText()
    }
    val cookie = CookieManager()
    CookieHandler.setDefault(cookie)
    val text = with(URL("https://adventofcode.com/$year/day/$day/input").openConnection() as HttpURLConnection){
        this.setRequestProperty("Cookie", "session=${File(storeDir, "session.txt").readLines()[0]}")
        inputStream.bufferedReader().readText()
    }
    file.writeText(text)
    return text
}


fun inputLines(day: Int, year: Int = 2020, filterBlank: Boolean = true): List<String> =
    input(day, year)
        .split("\n")
        .apIf(filterBlank) { it.filter(String::isNotBlank) }



fun <T> T.apIf(conditional: Boolean, block: (T) -> T): T = if(conditional) block(this) else this

enum class Part { ONE, TWO }

fun <T> List<T>.foldSameType(folder: (T, T) -> T, ifSizeIsZero: T? = null): T {
    if(isEmpty()) return ifSizeIsZero ?: error("size == 0, and there's no sizeofzero default value")
    var value = first()
    for(i in 1 until size) {
        value = folder(this[i], value)
    }
    return value
}





fun <T> permutations(input: List<T>, length: Int): List<List<T/*size=length*/>> {
    if(length == 0) return input.map { emptyList() }
    if(length == 1) return input.map { listOf(it) }
    return permutations(input, length - 1).flatMap { list -> input.map { list  + it } }
}
