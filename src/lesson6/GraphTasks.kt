@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import lesson6.impl.GraphBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */

fun Graph.findEulerLoop(): Graph {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
//S = O(n) T = O(n) где n - количество вершин в графе
fun Graph.minimumSpanningTree(): Graph {
//    val result = mutableListOf<Graph.Edge>()
//    var n = 0
//    for (v in this.vertices) {
//        n += this.getNeighbors(v).size % 2
//    }
//    if (n != 0) return result
//    val stack: Stack<Pair<Graph.Vertex, Graph.Edge>> = Stack()
//    stack.push(Pair(this.edges.first().begin, this.edges.first()))
//    val passed = mutableSetOf(stack.peek().second)
//    while (!stack.empty()) {
//        var neighbor = 0
//        var nextEdge = stack.peek().second
//        var nextVertex = stack.peek().first
//        when (stack.peek().first) {
//            stack.peek().second.begin -> nextVertex = stack.peek().second.end
//            stack.peek().second.end -> nextVertex = stack.peek().second.begin
//        }
//        for (t in getConnections(nextVertex).values) {
//            if (!passed.contains(t)) {
//                neighbor++
//                nextEdge = t
//            }
//        }
//        if (neighbor == 0) {
//            result += stack.pop().second
//        } else {
//            stack.push(Pair(nextVertex, nextEdge))
//            passed += nextEdge
//        }
//    }
//    return result


    val result: MutableList<Graph.Vertex> = ArrayList()
    val vertices: MutableSet<Graph.Vertex> = this.vertices
    var neighbors: MutableSet<Graph.Vertex> = HashSet()
    val graphR = GraphBuilder()
    var current: Graph.Vertex
    current = vertices.iterator().next()
    result.add(current)
    for (i in 0 until vertices.size - 1) {
        neighbors.addAll(this.getNeighbors(current))
        val iterator: Iterator<Graph.Vertex> = neighbors.iterator()
        while (iterator.hasNext() && result.contains(current)) {
            current = iterator.next()
        }
        if (result.size > 2 && current == result[result.size - 2]) break
        graphR.addVertex(current.toString())
        if (!result.isEmpty()) graphR.addConnection(result[result.size - 1], current, 1)
        result.add(current)
        neighbors.clear()
    }
    neighbors.clear()
    vertices.removeAll(result)
    for (vertex in vertices) {
        neighbors = this.getNeighbors(vertex)
        for (v in neighbors) {

            if (result.contains(v)) {
                result.add(vertex)
                graphR.addVertex(vertex.toString())
                graphR.addConnection(v, vertex, 1)
                break
            }
        }
    }
    return graphR.build()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    var graph = this
    if (graph.vertices.isEmpty()) {
        return emptySet()
    }
    if (graph.edges.isEmpty()) return graph.vertices
    val result = HashSet<Graph.Vertex>()
    val invResult = HashSet<Graph.Vertex>()
    val edges = graph.edges
    var first: Graph.Edge? = null
    for (edge in edges) {
        val eBegin = edge.begin
        val eEnd = edge.end

        if (result.isEmpty() || eBegin === first!!.begin) first = edge
        if (!result.contains(first!!.end)) {
            result.add(first.end)
            invResult.add(first.begin)
        }
        if (result.contains(eBegin)) invResult.add(eEnd)
        if (invResult.contains(eBegin)) result.add(eEnd)
    }
    return if (result.size > invResult.size)
        result
    else
        invResult
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    TODO()
}

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}