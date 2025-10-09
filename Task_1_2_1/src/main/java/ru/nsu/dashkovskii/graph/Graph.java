package ru.nsu.dashkovskii.graph;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;
import ru.nsu.dashkovskii.reader.GraphReader;
import ru.nsu.dashkovskii.strategy.TopologicalSortStrategy;

/**
 * Интерфейс графа с основными операциями.
 */
public interface Graph {

    /**
     * Добавить вершину в граф.
     *
     * @param vertex вершина для добавления
     */
    void addVertex(Vertex vertex);

    /**
     * Удалить вершину из графа.
     *
     * @param vertex вершина для удаления
     */
    void removeVertex(Vertex vertex);

    /**
     * Добавить ребро в граф.
     *
     * @param edge ребро для добавления
     */
    void addEdge(Edge edge);

    /**
     * Удалить ребро из графа.
     *
     * @param edge ребро для удаления
     */
    void removeEdge(Edge edge);

    /**
     * Получить список всех соседей вершины.
     *
     * @param vertex вершина
     * @return список соседних вершин
     */
    List<Vertex> getNeighbors(Vertex vertex);

    /**
     * Получить список всех вершин графа.
     *
     * @return список всех вершин
     */
    List<Vertex> getVertices();

    /**
     * Получить список всех рёбер графа.
     *
     * @return список всех рёбер
     */
    List<Edge> getEdges();

    /**
     * Проверить, содержит ли граф вершину.
     *
     * @param vertex вершина
     * @return true, если вершина есть в графе
     */
    boolean containsVertex(Vertex vertex);

    /**
     * Проверить, содержит ли граф ребро.
     *
     * @param edge ребро
     * @return true, если ребро есть в графе
     */
    boolean containsEdge(Edge edge);

    /**
     * Проверить, является ли граф ориентированным.
     *
     * @return true, если граф ориентированный
     */
    boolean isDirected();

    /**
     * Добавить ребро между двумя вершинами (default метод).
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     */
    default void addEdge(Vertex from, Vertex to) {
        addEdge(new Edge(from, to));
    }

    /**
     * Получить количество вершин в графе (default метод).
     *
     * @return количество вершин
     */
    default int getVertexCount() {
        return getVertices().size();
    }

    /**
     * Получить количество рёбер в графе (default метод).
     *
     * @return количество рёбер
     */
    default int getEdgeCount() {
        return getEdges().size();
    }

    /**
     * Удалить ребро между двумя вершинами (default метод).
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     */
    default void removeEdge(Vertex from, Vertex to) {
        removeEdge(new Edge(from, to));
    }

    /**
     * Прочитать граф из файла (default метод).
     *
     * @param filename путь к файлу
     * @throws IOException если возникла ошибка при чтении файла
     */
    default void readFromFile(String filename) throws IOException {
        GraphReader.readGraph(filename, this);
    }

    /**
     * Сравнение графов на равенство (default метод).
     * Два графа равны, если содержат одинаковые вершины и рёбра.
     *
     * @param other другой граф
     * @return true, если графы равны
     */
    default boolean equalsGraph(Graph other) {
        if (other == null) {
            return false;
        }

        if (this.isDirected() != other.isDirected()) {
            return false;
        }

        Set<Vertex> thisVertices = new HashSet<>(this.getVertices());
        Set<Vertex> otherVertices = new HashSet<>(other.getVertices());

        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        Set<Edge> thisEdges = new HashSet<>(this.getEdges());
        Set<Edge> otherEdges = new HashSet<>(other.getEdges());

        return thisEdges.equals(otherEdges);
    }

    /**
     * Установить стратегию топологической сортировки.
     *
     * @param strategy стратегия сортировки
     */
    void setTopologicalSortStrategy(TopologicalSortStrategy strategy);

    /**
     * Выполнить топологическую сортировку.
     *
     * @return список отсортированных вершин
     * @throws IllegalStateException если граф содержит цикл
     */
    List<Integer> topologicalSort();
}
