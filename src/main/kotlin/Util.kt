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
