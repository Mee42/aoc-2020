package day1

import inputLines





fun main1(): Int {
   val numberSet = inputLines(1).filter { it.isNotBlank() }.map { it.toInt() }.toSet()
   
   for(i in numberSet) {
      val need = 2020 - i
      if(numberSet.contains(need)) return i * need;
   }
   return -1
}

fun main2(): Int {
  val numberSet = inputLines(1).filter { it.isNotBlank() }.map { it.toInt() }.toSet()

  for(i in numberSet) {
     for(j in numberSet) {
        val need = 2020 - i - j
        if(numberSet.contains(need)) return i * j * need;
     }
  }
  return -1
}

fun main() = println(main2())