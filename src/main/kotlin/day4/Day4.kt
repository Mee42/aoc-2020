package day4

import input

val fields = listOf("byr","iyr","eyr","hgt","hcl","ecl","pid","cid").toSet()
val fields2 = listOf("byr","iyr","eyr","hgt","hcl","ecl","pid").toSet()


fun f(i: Int): Boolean {
    println(i)
    return true
}

fun main() {
    val answer = input(4).split("\n\n")
            .map { passport -> passport
                    .trim()
                    .split(Regex("""\s+"""))
                    .map { val (a, b) = it.split(":"); a to b}
            }.count { passport ->
                val keySet = passport.map(Pair<String, String>::first).toSet()
                val map = passport.toMap()
                (keySet == fields || keySet == fields2) &&
                        map.getValue("byr").toInt() in 1920..2002 &&
                        map.getValue("iyr").toInt() in 2010..2020 &&
                        map.getValue("eyr").toInt() in 2020..2030 &&
                        map.getValue("hgt").dropLast(2).toInt() in (if (map.getValue("hgt").takeLast(2) == "cm") 150..193 else 59..76) &&
                        map.getValue("hcl").startsWith("#") &&
                        map.getValue("hcl").length == 7 &&
                        map.getValue("hcl").substring(1).all { it in (('0'..'9') + ('a'..'f')) } &&
                        map.getValue("ecl") in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") &&
                        map.getValue("pid").length == 9 &&
                        map.getValue("pid").all { it in '0'..'9' }
            }

    println(answer)
}