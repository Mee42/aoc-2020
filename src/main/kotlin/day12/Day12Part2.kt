package day12

import inputLines
import kotlin.math.absoluteValue


fun main() {
    var wayXPosition = 10
    var wayYPosition = 1
    var meXPosition = 0
    var meYPosition = 0

    fun rotateWaypoint90Degrees() {
        val newX = - wayYPosition
        val newY = wayXPosition
        wayXPosition = newX
        wayYPosition = newY
    }


    inputLines(day = 12)
            .forEach { line ->
                val amount = line.drop(1).toInt()
                when(val action = line[0]) {
                    in "NEWS" -> {
                        val tempDirection = Direction.values().first { it.char == action }
                        wayXPosition += tempDirection.xOff * amount
                        wayYPosition += tempDirection.yOff * amount
                    }
                    'F' -> {
                        meXPosition += wayXPosition * amount
                        meYPosition += wayYPosition * amount
                    }
                    'R' -> {
                        for(i in 0 until amount/90) {
                            rotateWaypoint90Degrees()
                            rotateWaypoint90Degrees()
                            rotateWaypoint90Degrees()
                        }
                    }
                    'L' -> {
                        for(i in 0 until amount/90) {
                            rotateWaypoint90Degrees()
                        }
                    }
               }

            }
    println("$wayXPosition,$wayYPosition")
    println("$meXPosition,$meYPosition")
    //println((meXPosition - wayXPosition).absoluteValue + (meYPosition - wayYPosition).absoluteValue)
    println(meXPosition.absoluteValue + meYPosition.absoluteValue)
}