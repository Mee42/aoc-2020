package day2

import inputLines


fun main1(): Int = inputLines(day = 2)
        .filter(String::isNotBlank)
        .count { line ->
            val (lower, upper, letter, password) = line.trim().split(Regex("(-)|(: )|( )"))
            val count = password.count { char -> char.toString() == letter }
            count in lower.toInt()..upper.toInt()
}

fun main2(): Int = inputLines(day = 2)
        .filter(String::isNotBlank)
        .count { line ->
            val (lower, upper, letter, password) = line.trim().split(Regex("(-)|(: )|( )"))
            (password[lower.toInt() - 1] == letter[0]) xor (password[upper.toInt() - 1] == letter[0])
        }

fun main() = println(main2())

