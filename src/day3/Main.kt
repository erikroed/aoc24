package day3

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match
import java.io.File

fun main() {
    val source = "src"
    val day = 3
    val fileName = "input.txt"
    val filePath = "${source}/day${day}/${fileName}"

    val content = File(filePath).readLines(Charsets.UTF_8)

    //part1(content.joinToString(","))
    part2(content.joinToString(","))
}

private fun part1(content: String) {
    val pattern = Regex("""mul\(\s*(\d+)\s*,\s*(\d+)\s*\)""")
    val matches = pattern.findAll(content)
    var sum = 0
    matches.forEach {
        val (x, y) = it.destructured
        sum += (x.toInt() * y.toInt())
    }
    println(sum)
}

private fun part2(text: String) {
    val mulPattern = Regex("""mul\(\s*(\d+)\s*,\s*(\d+)\s*\)""")
    val dontPattern = Regex("""don't\(\)""")
    val doPattern = Regex("""do\(\)""")

    var sum = 0L
    var enabled = true

    """$mulPattern|$doPattern|$dontPattern""".toRegex().findAll(text).forEach { match ->
        when (match.value) {
            "don't()" -> enabled = false
            "do()" -> enabled = true
            else -> if (enabled) sum += match.multiplyNumbers()
        }
    }

    println(sum)
}

private fun MatchResult.multiplyNumbers(): Long {
    val (first, second) = destructured
    return first.toLong() * second.toLong()
}