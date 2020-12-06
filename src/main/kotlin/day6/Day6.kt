package day6


import foldSameType
import input


fun main() {
    println(input(day = 6).split("\n\n").map(String::trim).sumBy { group ->
        group.split("\n").map(String::toSet).foldSameType(Set<Char>::intersect).size
    })
}


fun main1() {
    println(input(day = 6).split("\n\n").map { it.replace("\n","").toSet() }.sumBy { it.size })
}

