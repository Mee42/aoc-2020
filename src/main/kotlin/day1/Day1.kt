package day1

import inputLines


fun main1(): Int {
    val numberSet = inputLines(1).filter { it.isNotBlank() }.map { it.toInt() }.toSet()

    for (i in numberSet) {
        val need = 2020 - i
        if (need in numberSet) return i * need
    }
    error("no solutions found")
}

fun main2(): Int {
    val numberSet = inputLines(1).filter { it.isNotBlank() }.map { it.toInt() }.toSet()

    for (i in numberSet) {
        for (j in numberSet) {
            val need = 2020 - i - j
            if (need in numberSet) return i * j * need
        }
    }
    error("no solutions found")
}

fun main() = println(main2())