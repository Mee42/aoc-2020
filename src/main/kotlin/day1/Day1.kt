package day1

import inputLines


fun run(i: Int): Int {
    return i.div(3) - 2
}

fun run2(i: Int): Int {
    return if(i <= 0) {
        0
    } else {
        val x = run(i)
        val y = run2(x).takeUnless { it <= 0 } ?: 0
        x + y
    }
}


fun main1() {
    println(inputLines(1, 2019).map { run(it.toInt()) }.sum())
}

fun main2() {
    println(inputLines(1, 2019).map { run2(it.toInt()) }.sum())
}

fun main() = main2()