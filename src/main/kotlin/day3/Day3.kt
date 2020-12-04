package day3

import inputLines

val sampleInput = """
        ..##.......
        #...#...#..
        .#....#..#.
        ..#.#...#.#
        .#...##..#.
        ..#.##.....
        .#.#.#....#
        .#........#
        #.##...#...
        #...##....#
        .#..#...#.#
    """.trimIndent().split("\n")

fun main1() {
     val map = inputLines(day = 3).map { it.trim() }.map { it.repeat(100) }
    treesWith(map, 3, 1)
}
fun treesWith(inputField: List<String>, colD: Int, rowD: Int): Int {

    var row = 0
    var col = 0
    var trees = 0
    while (true) {
        if(row >= inputField.size) break
        val tree = inputField[row][col]
        if (tree == '#') trees++
        row += rowD
        col += colD
    }
    return trees
}

fun main() {
    val map =
            inputLines(day = 3)
//                    sampleInput
                    .map { it.trim() }.map { it.repeat(100) }
    println(treesWith(map,1, 1) * treesWith(map, 3, 1) * treesWith(map, 5, 1) * treesWith(map, 7, 1) * treesWith(map, 1, 2))
}