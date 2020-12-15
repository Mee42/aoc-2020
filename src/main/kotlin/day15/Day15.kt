package day15

fun main() {
    
    val sampleInput = "0, 3, 6"
    val realInput = "10,16,6,0,1,17"
    val input = realInput.split(",").map { it.trim().toInt() }
    var lastNumberSaid = input[0]
    val lastSaidAt = mutableMapOf<Int, Int>()

    val hasBeenSaidBefore = mutableSetOf<Int>()

    for((i, value) in input.withIndex()) {
        if(i != input.lastIndex) {
            println("lastSaidAt[$value] = ${i + 1}")
            hasBeenSaidBefore.add(value)
            lastSaidAt[value] = i + 1
        }
        println("${i + 1} $value")
    }
    lastNumberSaid = input.last()
    for(i: Int in (input.size + 1)..30000000) {
//        val history = lastSaidAt[lastNumberSaid]!!
        if(hasBeenSaidBefore.contains(lastNumberSaid)) {
            val history = lastSaidAt[lastNumberSaid]!!
            val newValue = (i - 1) - history
//            println("newValue = $newValue = ${i - 1} - $history")
//            val list = lastSaidAt.computeIfAbsent(newValue, ::ArrayDeque)

            println("$i $newValue")
            lastSaidAt[lastNumberSaid] = i - 1
//            println("lastSaidAt[$lastNumberSaid] = ${i - 1}")
            lastNumberSaid = newValue
        } else {
            lastSaidAt[lastNumberSaid] = i - 1
            hasBeenSaidBefore.add(lastNumberSaid)
            lastNumberSaid = 0
            println("$i 0")
        }
    }
}