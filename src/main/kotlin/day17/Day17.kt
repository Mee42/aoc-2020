package day17

import kotlin.math.absoluteValue
import inputLines

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

// -3 -2, -1, 0, 1 ,2
// 0   1   2  3  4  5
// size   = 6
// size/2 = 3
data class Plane<T>(private val data: List<T>) {
    operator fun get(i: Int): T {
        return data[i + data.size/2]
    }
    fun setAndCopy(i: Int, value: T): Plane<T> {
        // double the size of the list so we can fit it
        return Plane(data.copySet(i + data.size/2, value))
    }
    // default will be called with the value at '0', in case that information is wanted
    fun expanded(default: (T) -> T): Plane<T> {
        return Plane(listOf(default(get(0))) + data + listOf(default(get(0))))
    }
    val indexes
        get() = -data.size/2..data.size/2
    
    
    fun <R> map(f: (T, Int) -> R): Plane<R> {
        return Plane(data.mapIndexed { i, value -> f(value, i - data.size/2) })
    }
}


fun <T> List<T>.copySet(i: Int, value: T): List<T> {
    val new = mutableListOf<T>()
    new.addAll(this)
    new[i] = value
    return new
}

private class Board<T> (private val space: Plane<Plane<Plane<T>>>, private val fToString: (T) -> Char){
//    constructor(starterValue: T, fToString: (T) -> Char): this(Plane(listOf(Plane(listOf(Plane(listOf(starterValue)))))), fToString)

    operator fun get(point: Point): T {
        return space[point.z][point.x][point.y]
    }
    fun getOrNull(point: Point): T? {
        if(point.z !in space.indexes || point.x !in space[point.z].indexes || point.y !in space[point.z][point.x].indexes) return null
        return get(point)
    }
    fun setAndCopy(point: Point,newValue : T): Board<T> {
        return this.mapSameType { p, value -> if(point == p) newValue else value }
    }
    fun <R> map(fToString: (R) -> Char, f: (Point, T) -> R): Board<R> {
        val newSpace = space.map { z, zIndex -> z.map { x, xIndex -> x.map { value, yIndex -> 
            val point = Point(xIndex, yIndex, zIndex)
            f(point, value)
        } } }
        return Board(newSpace, fToString)
    }
    fun mapSameType(f: (Point, T) -> T): Board<T> = map(fToString, f)
    
    fun expand(defaultValue: T): Board<T> {
        val newPlane =
                space.expanded { it.map { plane, _ -> plane.map { _, _ -> defaultValue } } }.map { xyPlane, _ ->
                    xyPlane.expanded { it.map { _, _ -> defaultValue } }.map { xPlane, _ -> xPlane.expanded { defaultValue } }
                }
        return Board(newPlane, fToString)
    }

    override fun toString(): String {
        var s = ""
        for(z in space.indexes) {
            s += "z = $z\n"
            for(y in space[0][0].indexes) {
                for(x in space[0].indexes) {
                    s += fToString(space[z][y][x])
                }
                s += "\n"
            }
            s += "\n"
        }
        return s
    }
     fun getAllPoints(predicate: (T) -> Boolean = { true }): Map<Point, T> =
             space.indexes.flatMap { zValue -> space[zValue].indexes.flatMap { xValue -> space[zValue][xValue].indexes.mapNotNull { yValue ->
                 val value = space[zValue][xValue][yValue]
                 if(predicate(value)) Point(xValue, yValue, zValue) to value else null
        } } }.toMap()

}

val sampleInput = """
.#.
..#
###
""".trim()

fun main() {
    val input =
//            sampleInput.split("\n")
            inputLines(day = 17)
    val newPlane: Plane<Plane<Plane<Boolean>>> = Plane(listOf(Plane(Plane(false.repeat(11)).repeat(11))))
    var board = Board(newPlane) { if(it) '#' else '.' }
//    println(board.getAllPoints())
//    println(board.getAllPoints())
//    return
    for((x, line) in input.withIndex()) {
        for((y, char) in line.withIndex()) {
            board = board.setAndCopy(Point(x = (x - 4), y = (y - 4), z = 0), char == '#')
        }
    }

    println(board)
    for(i in 0 until 6) {
        board = tick(board.expand(false))
        println("=== $i\n$board")
    }
    
    val count = board.getAllPoints().values.count { it }
    println(count)
}
// b should be expanded before calling
private fun tick(b: Board<Boolean>) = b.mapSameType { point, value ->
    val count = point.neighbors().count { b.getOrNull(it) ?: false }
//    if(value) println("$point -> $count")
    (value && count in 2..3) || (!value && count == 3)
}




fun <T> T.repeat(i: Int): List<T> {
    val n = mutableListOf<T>()
    for(x in 0 until i) n.add(this)
    return n
}




