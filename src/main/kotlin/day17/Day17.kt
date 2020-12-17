package day17


private data class Point(val x: Int, val y: Int, val z: Int) {
    fun neighbors(): List<Point> { 
        return (-1..1).flatMap { xOff ->
            (-1..1).flatMap { yOff ->
                (-1..1).mapNotNull { zOff ->
                    if(xOff == 0 && yOff == 0 && zOff == 0) null
                    else Point(x + xOff, y + yOff, z + zOff)
                }
            }
        }
    }
}

private class Board<T>(private val default: T, val fToString: (T) -> Char) {
    private var space: Plane<Plane<Plane<T>>> = Plane { Plane { Plane { default } } }
    operator fun get(point: Point): T {
        return space[point.z][point.x][point.y]
    }
    operator fun set(point: Point, value: T) {
        space[point.z][point.x][point.y] = value
    }

    override fun toString(): String {
        var s = ""
        for(z in space.indexes) {
            s += "z = $z\n"
            for(x in space[z].indexes) {
                for(y in space[z][x].indexes) {
                    s += fToString(space[z][x][y])
                }
                s += "\n"
            }
            s += "\n\n"
        }
        return s
    }
     fun getAllPoints(predicate: (T) -> Boolean = { true }): Map<Point, T> =
             space.indexes.flatMap { zValue -> space[zValue].indexes.flatMap { xValue -> space[zValue][xValue].indexes.mapNotNull { yValue ->
                 val value = space[zValue][xValue][yValue]
                 if(predicate(value)) Point(xValue, yValue, zValue) to value else null
        } } }.toMap()
    
    fun copy(copy: (T) -> T): Board<T> {
        val new = Board(default, fToString)
        new.space = space.copy { a -> a.copy { it.copy(copy) } }
        return new
    }
}

val sampleInput = """
.#.
..#
###
""".trim()

fun main() {
    var board = Board(false) { if(it) '#' else '.' }
    val input = sampleInput.split("\n")
    for((x, line) in input.withIndex()) {
        for((y, char) in line.withIndex()) {
            board[Point(x = x, y = y, z = 0)] = (char == '#')
        }
    }


    println(board)
    val new = board.copy { it }
//    for((point,  isActive) in board.getAllPoints()) {
//        println("$point -> $isActive")
//        for (p in point.neighbors()) {
//            board[p] // get, make sure the thing is expanded
//        }
//    }
    println("==")
    for((point, isActive) in board.getAllPoints()) {
        val count = point.neighbors().count { board[it] }
        val newValue = (isActive && count in 2..3) || (!isActive && count == 2)
        if(newValue != isActive) new[point] = newValue
    }
    board.getAllPoints().forEach { (point, value) -> println("$point -> $value")}
    println("=======\n$new")
}