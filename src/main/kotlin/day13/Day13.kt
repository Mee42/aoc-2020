package day13

import input
import inputLines
import Maybe
import apIf
import foldSameType

val sample = """
939
7,13,x,x,59,x,31,19
""".trim()

fun lcm(first: Long, second: Long): Long {
    //Largest from both numbers, get as initial lcm value
    var lcm = if(first>second) first else second

    //Running Loop to find out LCM
    while (true){
        //check lcm value divisible by both the numbers
        if(lcm%first==0L && lcm%second==0L){
            //break the loop if conditon satisfies
            break;
        }
        //increase lcm value by 1
        lcm++
    }
    return lcm
}

/* returns x where (a * x) % b == 1 */
fun multInv(a: Long, b: Long): Long {
    if (b == 1L) return 1
    var aa = a
    var bb = b
    var x0 = 0L
    var x1 = 1L
    while (aa > 1) {
        val q = aa / bb
        var t = bb
        bb = aa % bb
        aa = t
        t = x0
        x0 = x1 - q * x0
        x1 = t
    }
    if (x1 < 0) x1 += b
    return x1
}

fun chineseRemainder(n: LongArray, a: LongArray): Long {
    val prod = n.fold(1L) { acc, i -> acc * i }
    var sum = 0L
    for (i in n.indices) {
        val p = prod / n[i]
        sum += a[i] * multInv(p, n[i]) * p
    }
    return sum % prod
}

fun main() {
    val (inp, x) =
            inputLines(day = 13)
//            sample.split("\n")

    //val number = inp.toInt()
    val busses: Iterable<IndexedValue<Long?>> = x.split(",").map { if(it == "x") null else it.toLong() }.withIndex()

//    var number = 0L
//    for(i in filtered.indices) {
//        val product: Long = filtered.subList(i, filtered.size).product()
//        println(product)
//        number += product
//    }
//    println(filtered)
//    println(number)

//    println(chineseRemainder(
//            listOf(17, 13, 19).toIntArray(),
//            listOf(17 - 0, 13 -  2, 19 -  3).toIntArray()
//    ))
    println(chineseRemainder(
            busses.mapNotNull { (i, bus) -> bus }.toLongArray(),
            busses.mapNotNull { (i, bus) -> if(bus == null) null else bus - i }.toLongArray()
    ))

}

fun List<Long>.product(): Long {
    return this.fold(1L) { a, b -> a * b }
}