package day18

import inputLines

fun main() {
    println(eval(lex("1 + (2 * 3) + (4 * (5 + 6))")))
//    println(eval(lex("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))
//    println(eval(lex("5 + (8 * 3 + 9 + 3 * 4 * 3)")))
    println(inputLines(day = 18)
            .filter { it.isNotBlank() }
            .map { eval(lex(it)).toLong() }.sum())

    // not 1739613964
    
    
    // part two:
    // not 25675
}

enum class TType { INT, OP, LPAREN, RPAREN}
data class Token(val type: TType, val content: String)

fun lex(str: String): List<Token> {
    if(str.isEmpty()) return emptyList()
    return when(str[0]){
        ' ' -> lex(str.substring(1))
        '(' -> listOf(Token(TType.LPAREN, "(")) + lex(str.substring(1))
        ')' -> listOf(Token(TType.RPAREN, "R")) + lex(str.substring(1))
        '*' -> listOf(Token(TType.OP, "*")) + lex(str.substring(1))
        '+' -> listOf(Token(TType.OP, "+")) + lex(str.substring(1))
        in '0'..'9' -> {
            val indexOfNot = str.indexOfFirst { it !in '0'..'9' }.takeUnless { it == -1 } ?: str.length
            val int = str.substring(0, indexOfNot)
            listOf(Token(TType.INT, int)) + lex(str.substring(indexOfNot))
        }
        else -> error("can't lex $str")
    }
}

fun eval(tokens: List<Token>): Long {
    var (value, rest) = evalFirst(tokens)
//    var rest: List<Token> = tokens
    val list = mutableListOf(value.toString()) // ints separated by operators

    while(rest.isNotEmpty()) {
        val op = rest.first()
        val restExpr = rest.drop(1)
        val (newValue, nextRest) = evalFirst(restExpr)
        rest = nextRest
        list.add(op.content)
        list.add(newValue.toString())
    }

    val x = list.splitBy { it == "*" }.map { segment ->
        // everything in this segment must be added together
        segment
                .filter { it != "+" }
                .map { it.toLong() }
                .sum()
    }.fold(1L, Long::times)



    return x
}

fun <T> List<T>.splitBy(predicate: (T) -> Boolean): List<List<T>> {
    if(isEmpty()) return emptyList()
    if(predicate(first())) return emptyList<List<T>>() + this.drop(1).splitBy(predicate)
    var i = 0
    while(++i < size) {
        if(predicate(this[i])) {
            // split at 'i'
            break
        }
    }
    val left = subList(0, i)
    val right = if(i == size) emptyList() else subList(i + 1, size)
    return if(right.isEmpty()) listOf(left)
    else listOf(left) + right.splitBy(predicate)
}

fun evalFirst(tokens: List<Token>): Pair<Long, List<Token>> {
    val first = tokens.first()
    when(first.type) {
        TType.INT -> return first.content.toLong() to tokens.drop(1)
        TType.OP -> error("not expecting an operator")
        TType.LPAREN -> {
            var count = 0
            var i = 0
            while(true) when(tokens[++i].type) {
                TType.LPAREN -> count++
                TType.RPAREN -> if(count == 0) break else count--
                else -> {}
            }
            val innerExpr = tokens.subList(1, i)
            val outerExpr = tokens.subList(i + 1, tokens.size)
            return eval(innerExpr) to outerExpr
        }
        else -> error("can't handle token $first in evalFirst")
    }
}
