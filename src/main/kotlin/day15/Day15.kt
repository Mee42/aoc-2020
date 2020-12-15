package day15

import kotlin.math.max
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

fun main() {
    val x = measureTimeMillis {
        val sampleInput = "0, 3, 6"
        val realInput = "10,16,6,0,1,17"
        val input = realInput.split(",").map { it.trim().toInt() }
        var lastNumberSaid = input[0]
        val lastSaidAt = mutableMapOf<Int, Int>()


        for ((i, value) in input.withIndex()) {
            if (i != input.lastIndex) {
//            println("lastSaidAt[$value] = ${i + 1}")
                lastSaidAt[value] = i + 1
            }
//        println("${i + 1} $value")
        }
        lastNumberSaid = input.last()
        var maxNumber = 0
        for (i: Int in (input.size + 1)..30000000) {
            val history = lastSaidAt[lastNumberSaid]
            if (history != null) {
                val newValue = (i - 1) - history
                lastSaidAt[lastNumberSaid] = i - 1
                lastNumberSaid = newValue
            } else {
                lastSaidAt[lastNumberSaid] = i - 1
                lastNumberSaid = 0
            }
            maxNumber = max(lastNumberSaid, maxNumber)
        }
        println(">>$lastNumberSaid")
        println("max: $maxNumber")
    }
    println(x / 1000)
}