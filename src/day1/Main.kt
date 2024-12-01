package day1

import java.io.File
import kotlin.math.abs

fun main() {
    val source = "src"
    val day = 1
    val fileName = "input.txt"
    val filePath = "${source}/day${day}/${fileName}"

    val content = File(filePath).readText(Charsets.UTF_8)

    part1(content)
    part2(content)
}

private fun part2(content: String) {
    val (firstColumn, secondColumn) = getLists(content)
    val totalSimilarityScore = mutableListOf<Int>()

    firstColumn.forEach { leftValue ->
        val occurrences = secondColumn.count { it -> it == leftValue }.toInt()
        val similarity = leftValue * occurrences
        totalSimilarityScore.add(similarity)
    }

    println("Similarity score: ${totalSimilarityScore.sum()}")
}

private fun part1(content: String) {
    val (firstColumn, secondColumn) = getLists(content)
    val totalDistance = mutableListOf<Int>()
    content.lines()
        .filter(String::isNotBlank)
        .forEach { line ->
            val lowestFirstValue = firstColumn.min()
            firstColumn.remove(lowestFirstValue)

            val lowestSecondValue = secondColumn.min()
            secondColumn.remove(lowestSecondValue)

            val distance = abs(lowestSecondValue - lowestFirstValue)
            totalDistance.add(distance)
        }

    println("Total distance: ${totalDistance.sum()}")
}

private fun getLists(content: String): Pair<MutableList<Int>, MutableList<Int>> {
    val firstColumn = mutableListOf<Int>()
    val secondColumn = mutableListOf<Int>()

    content.lines().forEach { line ->
        val column = line.split("\\s+".toRegex())
        if (column.size == 2) {
            firstColumn.add(column[0].toInt())
            secondColumn.add(column[1].toInt())
        }
    }

    return Pair(firstColumn, secondColumn)
}