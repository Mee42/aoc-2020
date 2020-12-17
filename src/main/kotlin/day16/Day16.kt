package day16
import input
val sample = """
class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12
""".trim()

val sample2 = """
class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9
"""


fun main() {
    val (rulesIn, myTicket, otherTickets) =
            input(day = 16)
//            sample2
                    .split("\n\n")

    val rules = rulesIn.split("\n").filter { it.isNotBlank() }.map { line ->
        line.trim().grab(Regex("[a-z ]+"))[0].value to line.grab(Regex("""(\d+)-(\d+)""")).map { it.groupValues[1].toInt()..it.groupValues[2].toInt() }
    }.map { (a, b) -> a to b.fold(emptySet(), Set<Int>::plus) }
    println(rules)

    val allRules = rules.map { it.second }.fold(emptySet(), Set<Int>::plus)

    val myTicketParsed = myTicket.split("\n")[1].trim().split(",").map { it.toInt() }
    
    val remainingTickets = otherTickets.split("\n").drop(1)
            .filter { it.isNotEmpty() }
            .map { line -> line.split(",").map { it.toInt() } }
            .filter { line -> line.none { it !in allRules } } + listOf(myTicketParsed)
    
    println(remainingTickets)
    // part 1: 23044


    val columns = remainingTickets[0].indices


    var result = columns.map { colIndex ->
        // okay, we want to figure out all possible valid rules for this column
        colIndex to rules.filter { (ruleName, ruleRule) ->
            remainingTickets.all { ticket -> ticket[colIndex] in ruleRule }
        }.map(Pair<String, Set<Int>>::first)
    }
    
    while(result.count { it.second.size != 1 } != 0) {
        val decidedRuleNames = result.map { it.second }.filter { it.size == 1 }.map { it.first() }
        result = result.map { (colIndex, validRuleNames) -> colIndex to (if(validRuleNames.size == 1) validRuleNames else validRuleNames.filter { name -> name !in decidedRuleNames }) }
        println(result)
    }
    val finalResult = result.toMap().map { (columnIndex, v) -> v.first() to columnIndex }.toMap()
    println(finalResult)

    val departureColumns = finalResult.keys.filter { "departure" in it }
    val answer = departureColumns.map { myTicketParsed[finalResult[it]!!] }
    println(answer.fold(1L) { a, b -> a * b.toLong() })
}


fun String.grab(r: Regex): List<MatchResult> {
    return r.findAll(this).toList()
}