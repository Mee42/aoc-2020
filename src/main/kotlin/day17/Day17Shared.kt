package day17



val sampleInput = """
.#.
..#
###
""".trim()

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

fun <T> T.repeat(i: Int): List<T> {
    val n = mutableListOf<T>()
    for(x in 0 until i) n.add(this)
    return n
}

