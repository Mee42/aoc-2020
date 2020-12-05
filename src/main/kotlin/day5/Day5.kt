package day5

import inputLines

fun getPos(input: String): Pair<Int, Int> =
    input.take(7).replace("F", "0").replace("B", "1").toInt(2) to
    input.drop(7).replace("L", "0").replace("R", "1").toInt(2)


fun main() {
    val r: List<Int> = inputLines(day = 5)
            .map(::getPos)
            .map { (row, col) -> row*8 + col }
            .sorted()
    println(r.zip(r.drop(1)).firstOrNull { (a, b) -> a + 1 != b }!!.first)
}