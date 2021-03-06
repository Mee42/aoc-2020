package day17.part2

import inputLines
import day17.*



private data class Point(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun neighbors(): List<Point> {
        return (-1..1).flatMap { xOff ->
            (-1..1).flatMap { yOff ->
                (-1..1).flatMap { zOff ->
                    (-1..1).mapNotNull { wOff ->
                        if (xOff == 0 && yOff == 0 && zOff == 0 && wOff == 0) null
                        else Point(x + xOff, y + yOff, z + zOff, w + wOff)
                    }
                }
            }
        }
    }
}


private class Board<T> (private val space: Plane<Plane<Plane<Plane<T>>>>, private val fToString: (T) -> Char){

    operator fun get(point: Point): T {
        return space[point.z][point.w][point.x][point.y]
    }
    fun getOrNull(point: Point): T? {
        if(point.z !in space.indexes || point.w !in space[point.z].indexes || point.x !in space[point.z][point.w].indexes || point.y !in space[point.z][point.w][point.x].indexes) return null
        return get(point)
    }
    fun setAndCopy(point: Point, newValue : T): Board<T> {
        return this.mapSameType { p, value -> if(point == p) newValue else value }
    }
    fun <R> map(fToString: (R) -> Char, f: (Point, T) -> R): Board<R> {
        val newSpace = space.map { z, zIndex -> z.map { w, wIndex -> w.map { x, xIndex -> x.map { value, yIndex ->
            val point = Point(xIndex, yIndex, zIndex, wIndex)
            f(point, value)
        } }} }
        return Board(newSpace, fToString)
    }
    fun mapSameType(f: (Point, T) -> T): Board<T> = map(fToString, f)

    fun expand(defaultValue: T): Board<T> {
        val newPlane =
                space.expanded { it.map { plane, _ -> plane.map { plane0, _ -> plane0.map { _, _ -> defaultValue } }  } }
                        .map { wxyPlane, _ ->
                            wxyPlane.expanded { a -> a.map { plane, i -> plane.map { _, _ -> defaultValue } } }
                                    .map { xyPlane, _ ->
                                        xyPlane.expanded { it.map { _, _ -> defaultValue } }.map {
                                            xPlane, _ -> xPlane.expanded { defaultValue }
                                        }
                                    }
                        }
        return Board(newPlane, fToString)
    }

    override fun toString(): String {
        var s = ""
        for(z in space.indexes) {
            for(w in space[0].indexes) {
                s += "z = $z w = $w\n"
                for (y in space[0][0][0].indexes) {
                    for (x in space[0][0].indexes) {
                        s += fToString(space[z][w][y][x])
                    }
                    s += "\n"
                }
            }
            s += "\n"
        }
        return s
    }
    fun getAllPoints(predicate: (T) -> Boolean = { true }): Map<Point, T> =
            space.indexes.flatMap { zValue -> space[zValue].indexes.flatMap { wValue -> space[zValue][wValue].indexes.flatMap { xValue -> space[zValue][wValue][xValue].indexes.mapNotNull { yValue ->
                val value = space[zValue][wValue][xValue][yValue]
                if(predicate(value)) Point(xValue, yValue, zValue, wValue) to value else null
            } } } }.toMap()


}














fun main() {
    val input =
//            sampleInput.split("\n")
            inputLines(day = 17)
    val newPlane: Plane<Plane<Plane<Plane<Boolean>>>> = Plane(listOf(Plane(listOf(Plane(Plane(false.repeat(11)).repeat(11))))))
    var board = Board(newPlane) { if (it) '#' else '.' }
//    println(board.getAllPoints())
//    println(board.getAllPoints())
//    return
    for((x, line) in input.withIndex()) {
        for((y, char) in line.withIndex()) {
            board = board.setAndCopy(Point(x = (x - 4), y = (y - 4), z = 0, w = 0), char == '#')
        }
    }

    println(board)
    for(i in 0 until 6) {
        board = tick(board.expand(false))
        println("=== $i \n$board")
    }

    val count = board.getAllPoints().values.count { it }
    println(count)
}
// b should be expanded before calling
private fun tick(b: Board<Boolean>) = b.mapSameType { point, value ->
    val count = point.neighbors().count { b.getOrNull(it) ?: false }
//    if(value) println("$point -> $count")
    (value && count in 2..3) || (!value && count == 3)
//    count == 1
}








