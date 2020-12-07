

fun parse(str: String): Map<String, List<String>> {
    val map: MutableMap<String, List<String>> = str.split(".")
            .filter { it.isNotBlank() }
            .map { line ->
                val r = line.split("contain")
                val (name: String, rest) = r
                val contains: List<String> = rest.split(",").flatMap {
                    val (i, rest2) = it.trim().split(" ", limit = 2)
                    Array(i.toIntOrNull() ?: 0) { rest2 }.toList()
                }
                name.trim().replace("bags", "bag") to contains.map { it.trim().replace("bags", "bag") }
            }.toMap().toMutableMap()
    map["no other bag"] = emptyList()
    return map
}

fun main1() {
    val map = parse(input(day = 7)).map { (key, value) -> key to value.toSet() }.toMap()
    fun canGetToGoldBag(name: String): Boolean {
        return name == "shiny gold bag" || map.getValue(name).any(::canGetToGoldBag)
    }
    println(map.keys.count(::canGetToGoldBag) - 1)
}

fun main2() {
    val map = parse(input(day = 7))
    fun subBags(name: String): Int {
        return 1 + map[name]!!.sumBy(::subBags)
    }
    println(subBags("shiny gold bag") - 1)
}
fun main() = main2()