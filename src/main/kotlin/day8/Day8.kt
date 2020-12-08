package day8

import inputLines
import Just
import Maybe
import None
import flatten
import maybeIf
import maybe
import fromJust
import Part

val sample = """
nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
""".trim().split("\n")

fun main2() {
    println(runWith(0, inputLines(day = 8), emptySet(), 0, false, part = Part.TWO).fromJust())
}
fun main1() {
    println(runWith(0, inputLines(day = 8), emptySet(), 0, false, Part.ONE).fromJust())
}
fun main() = main1()


// returns acc after the given end condition is met
//   Part.ONE -> program hits infinite loop
//   Part.TWO -> program hits the end of the input on some valid path

// precoditions: if part = Part.TWO, changedYet must equal true
fun runWith(i: Int, input: List<String>, visited: Set<Int>, acc: Int, changedYet: Boolean, part: Part): Maybe<Int> {
    if(i >= input.size) {
        when(part) {
            Part.ONE -> error("reached end of input in day 1 part 1")
            Part.TWO -> return Just(acc)
       }
    }
    val line = input[i]
    if(visited.contains(i)) {
        return when(part) {
            Part.ONE -> Just(acc)
            Part.TWO -> None()
        }
    }
    val newVisited = visited + i
    val (instr, number) = line.split(" ")
    val processNop = { changed: Boolean -> runWith(i + 1, input, newVisited, acc, changed, part) }
    val processJmp = { changed: Boolean -> runWith(i + number.toInt(), input, newVisited, acc, changed, part) }

    return when(instr) {
        "nop" -> maybe(!changedYet) { processJmp(true) }.flatten() orElse { processNop(changedYet) }
        "acc" -> runWith(i + 1, input, newVisited, acc + number.toInt(), changedYet, part)
        "jmp" -> maybe(!changedYet) { processNop(true) }.flatten() orElse { processJmp(changedYet) }
        else -> TODO()
    }
}