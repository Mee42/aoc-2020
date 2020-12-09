package day9

import inputLines
import permutations

fun main() = main2()

fun main1() {
    println(run1(inputLines(day = 9).map { it.toLong() }, 25))
}

fun main2() {
    println(run2(inputLines(day = 9).map { it.toLong() }, 675280050))
}

fun run1(list: List<Long>, count: Int): Long {
    val isValid = permutations(list.take(count), 2).any { (a, b) -> a + b == list[count] }
    return if(!isValid) list[count]
    else                run1(list.drop(1), count)
}

fun run2(list: List<Long>, target: Long): Long {
    if(list.size < 2) error("reached end of input")
    var sum = list[0] + list[1]
    var index = 2
    while(sum < target) {
        sum += list[index++]
    }
    if(sum == target) {
        val n = list.take(index)
        return n.maxByOrNull { it }!! + n.minByOrNull { it }!!
    }
    return run2(list.drop(1), target)
}
