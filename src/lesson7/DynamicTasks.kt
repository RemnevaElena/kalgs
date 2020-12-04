@file:Suppress("UNUSED_PARAMETER")

package lesson7

import java.io.BufferedReader
import java.io.FileReader
import java.util.*


/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
// О(nm), S(nm) где n, m - длины исходных строк
fun longestCommonSubSequence(first: String, second: String): String {
    val firstChar = first.toCharArray()
    val secondChar = second.toCharArray()
    val firstLen = firstChar.size
    val secondLen = secondChar.size
    val matrix = Array(firstLen + 1) { IntArray(secondLen + 1) }

    for (i in 0..firstLen) {
        matrix[i][0] = 0
    }
    for (i in 0..secondLen) {
        matrix[0][i] = 0
    }

    for (i in 1..firstLen) {
        for (j in 1..secondLen) {
            if (firstChar[i - 1] == secondChar[j - 1]) {
                matrix[i][j] = matrix[i - 1][j - 1] + 1
            } else {
                matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1])
            }
        }
    }

    var i = firstLen
    var j = secondLen
    val result = StringBuilder()

    while (i > 0 && j > 0) {
        if (firstChar[i - 1] == secondChar[j - 1]) {
            result.append(firstChar[i - 1])
            i--
            j--
        } else {
            if (matrix[i][j - 1] > matrix[i - 1][j]) {
                j--
            } else {
                i--
            }
        }
    }
    return result.reverse().toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
//O(n^2), S(n)
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    if (list.isEmpty()) return result
    val size = list.size
    val arrayLength: Array<Int?> = Array(size) { -1 }
    val arrayPath: Array<Int?> = Array(size) { -1 }
    var max: Int?
    var jMax: Int?
    for (i in 0 until size) {
        max = 0
        jMax = 0
        for (j in 0 until i) {
            if (list[j] < list[i] && max != null) {
                if (arrayLength[j]!! > max) {
                    max = arrayLength[j]
                    jMax = j
                }
            }
        }
        arrayLength[i] = max!! + 1
        arrayPath[i] = jMax
    }
    var n = 0
    for (k in 0 until size) {
        if (arrayLength[k]!! > arrayLength[n]!!) n = k
    }
    while (true) {
        if (n == 0) {
            result.add(list[n])
            break
        }
        result.add(list[n])
        n = arrayPath[n]!!
    }

    result.reverse()
    return result
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5