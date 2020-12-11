package day11

import inputLines
import input

val sampleInput = """
L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
""".trim()


fun main() {
    var board = Board(
            input(day = 11)
//            sampleInput
                    .trim().split("\n").map { it.trim() }.map { it.map { it } })
    for(i in 0..100000) {
        val newBoard = runTickPart2(board)
        println(newBoard.countOccupied())
        println("" + newBoard + "\n\n\n")
        if(newBoard == board) {
            println("" + board + "\n\n\n" + newBoard)
            break
        }
        board = newBoard
    }
    println("boards are equal: " + board.countOccupied())
}


class Point(val row: Int, val col: Int)

class Board(val list: List<List<Char>>) {
    val rowSize = list.size
    val colSize = list[0].size
    fun at(point: Point): Char {
        return list[point.row][point.col]
    }
    fun setAt(point: Point, newValue: Char): Board {
        return Board(list.mapIndexed { row, rowList  -> rowList.mapIndexed { col, oldValue -> if(row == point.row && col == point.col) newValue else oldValue } })
    }

    override fun toString(): String {
        return list.joinToString("\n") { it.joinToString("") }
    }
    fun countOccupied(): Int {
        return list.sumBy { it.count { it == '#' } }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Board) return false

        for(r in 0 until rowSize) {
            for(c in 0 until colSize) {
                if(this.at(Point(r, c)) != other.at(Point(r, c))) return false
            }
        }

        return true
    }
}

val around = (-1 .. 1).flatMap { b -> (-1..1).map { a -> b to a } }
fun runTick(input: Board): Board {
    var board = input
    for(r in 0 until input.rowSize) {
        for(c in 0 until input.colSize) {
            val char = input.at(Point(r, c))
            if(char == '.') continue
            var count = 0
            for((rOff, cOff) in around) {
                if(rOff == 0 && cOff == 0) continue // don't look at the current square
                val newR = rOff + r
                val newC = cOff + c
                if(newR !in 0 until input.rowSize || newC !in 0 until input.colSize) continue // skip when out of bounds
                if(input.at(Point(newR, newC)) == '#') count++
            }
            val newChar = when(char) {
                'L' -> if(count == 0) '#' else char
                '#' -> if(count >= 4) 'L' else '#'
                else -> error("no")
            }
            if(newChar != char) board = board.setAt(Point(r, c), newChar)
        }
    }
    return board
}


fun runTickPart2(input: Board): Board {
    val outOfBounds = { r: Int, c: Int -> r !in 0 until input.rowSize || c !in 0 until input.colSize }
    var board = input
    for(r in 0 until input.rowSize) {
        for(c in 0 until input.colSize) {
            val char = input.at(Point(r, c))
            if(char == '.') continue
            var count = 0
            for((rOff, cOff) in around) {
                if(rOff == 0 && cOff == 0) continue // don't look at the current square
                var newR = rOff + r
                var newC = cOff + c
                while(!outOfBounds(newR, newC) && input.at(Point(newR, newC)) == '.') {
                    newR += rOff
                    newC += cOff
                }
                if(outOfBounds(newR, newC)) continue // skip when out of bounds
                if(input.at(Point(newR, newC)) == '#') count++
            }
            val newChar = when(char) {
                'L' -> if(count == 0) '#' else char
                '#' -> if(count >= 5) 'L' else '#'
                else -> error("no")
            }
            if(newChar != char) board = board.setAt(Point(r, c), newChar)
        }
    }
    return board
}