package day12

import inputLines
import kotlin.math.absoluteValue

enum class Direction(val xOff: Int, val yOff: Int, val char: Char) { 
    EAST(1, 0, 'E'), 
    SOUTH(0, -1, 'S'), 
    WEST(-1, 0, 'W'), 
    NORTH(0, 1, 'N');
    
    fun next() = when(this) {
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
        NORTH -> EAST
    }
    fun previous() = this.next().next().next()
}
val sample = """F10
N3
F7
R90
F11"""

fun main() {
    var xPosition = 10
    var yPosition = 1
    var direction = Direction.EAST
    fun move(direction: Direction, amount: Int) {
        xPosition += direction.xOff * amount
        yPosition += direction.yOff * amount
    }
    inputLines(day = 12)
//    sample.split("\n")
            .forEach { line ->
                val amount = line.drop(1).toInt()
                when(val action = line[0]) {
                    in "NSEW" -> {
                        val tempDirection = Direction.values().first { it.char == action }
                        move(tempDirection, amount)
                    }
                    'F' -> {
                        move(direction, amount)
                    }
                    'R' -> {
                        for(i in 0 until amount / 90) {
                           direction = direction.next()
                        }
                    }
                    'L' -> {
                        for(i in 0 until amount / 90) { 
                            direction = direction.previous()
                        }
                    }
               }
            }
    println(xPosition)
    println(yPosition)
    println(xPosition.absoluteValue + yPosition.absoluteValue)
}