package day2

import java.io.File
import kotlin.math.abs
import kotlin.math.absoluteValue

fun main() {
    val source = "src"
    val day = 2
    val fileName = "input2.txt"
    val filePath = "${source}/day${day}/${fileName}"

    val content = File(filePath).readLines(Charsets.UTF_8)

    part1(content)
    part2(content)
}

private fun part1(content: List<String>) {
    var numOfSafe = 0
    content
        .filter(String::isNotBlank)
        .map { it -> it.split("\\s".toRegex()).map(String::toInt) }
        .forEach { line ->
            if (isAllIncreasing(line) or isAllDecreasing(line)) {
                if (verifyDistance(line)) {
                    numOfSafe++
                }
            }
        }

    println("$numOfSafe reports is safe")
}

private fun verifyDistance(list: List<Int>): Boolean {
    return list.zipWithNext().all { (a, b) -> abs(a - b) <= 3 }
}

private fun isAllIncreasing(list: List<Int>): Boolean {
    for (i in 1 until list.size) {
        if (list[i] <= list[i - 1]) {
            return false
        }
    }

    return true
}

private fun isAllDecreasing(list: List<Int>): Boolean {
    for (i in 1 until list.size) {
        if (list[i] >= list[i - 1]) {
            return false
        }
    }

    return true
}

private fun part2(content: List<String>) {
    var numOfSafe = 0
    for (line in content) {
        val numbers = line.split("\\s".toRegex()).map(String::toInt)
        var safe = false
        for (i in 0..numbers.lastIndex) {
            safe = isLineSafe(numbers.toMutableList().apply { removeAt(i) })
            if (safe) break
        }

        if (safe) numOfSafe++
    }

    println("$numOfSafe reports is safe")
}

private fun isLineSafe(numbers: MutableList<Int>): Boolean {
    var safe = true
    var isUp = true
    var isDown = true
    for (i in 0..<numbers.lastIndex) {
        val a = numbers[i]
        val b = numbers[i + 1]
        safe = safe && ((a - b).absoluteValue <= 3)
        when {
            a < b -> isDown = false
            b < a -> isUp = false
            else -> {
                isUp = false
                isDown = false
            }
        }
    }
    return safe && (isUp || isDown)
}