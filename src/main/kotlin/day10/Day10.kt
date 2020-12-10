package day10

import inputLines
import kotlin.math.absoluteValue

val sample = """
16
10
15
5
1
11
7
19
6
12
4
""".trim().split("\n")
val sample2 = """
28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3
""".trim().split("\n")


val input =
        inputLines(day = 10)
//        sample
                .map(String::toInt).sorted()
fun main() {
    println("input size: " + input.size)
    println(input)
    println(sumIfStartingAt)
    println(part2(0, 0, input.maxOrNull()!!));
//    val result = input.zip(input.drop(1)).map { (a,b) -> b - a }.groupBy { it }.mapValues { (_, value) -> value.size + 1 }
//    println(result)
//    println(result[3]!! * result[1]!!)
}

val sumIfStartingAt = input.mapIndexed { index, i -> index to (i - input.last()).absoluteValue + 1 }.toMap()


var i = 0

data class Input(val startingIndex: Int, val currentValue: Int)

val lookup = mutableMapOf<Input, Long>()

// takes in the list of all remaining adapters
fun part2(startingIndex: Int, currentValue: Int, target: Int): Long {
    val look = lookup[Input(startingIndex, currentValue)]
    if(look != null) return look
    if(currentValue == target) return 1
    if(startingIndex >= input.size) return 0
    if(startingIndex < 50) {
        print("iteration step ${i++}   ")
        println("PART 2 start: $startingIndex current:$currentValue target:$target")
    }
    if(sumIfStartingAt[startingIndex]!! + currentValue + 3 <= target)
        return 0

    val useable = input.asSequence().drop(startingIndex).filter { it - 3 <= currentValue }.toList()
//    println("useable: $useable")
//    if(useable.isEmpty()) return 0
    var sum : Long = 0
    for((index, adapter) in useable.withIndex()) {
//        println("trying $adapter")
        sum += part2(index + startingIndex + 1, currentValue = adapter, target = target)
    }
    lookup[Input(startingIndex, currentValue)] = sum
    return sum
}