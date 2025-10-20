package ru.nsu.dashkovskii.model;

import java.util.Objects;

/**
 * Класс, представляющий ребро графа.
 */
public class Edge {
    private final Vertex from;
    private final Vertex to;
    private final int weight;

    /**
     * Конструктор ребра с весом.
     *
     * @param from   начальная вершина
     * @param to     конечная вершина
     * @param weight вес ребра
     */
    public Edge(Vertex from, Vertex to, int weight) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Вершины ребра не могут быть null");
        }
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * Конструктор ребра без веса (вес = 1 по умолчанию).
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     */
    public Edge(Vertex from, Vertex to) {
        this(from, to, 1);
    }

    /**
     * Получить начальную вершину.
     *
     * @return начальная вершина
     */
    public Vertex from() {
        return from;
    }

    /**
     * Получить конечную вершину.
     *
     * @return конечная вершина
     */
    public Vertex to() {
        return to;
    }

    /**
     * Получить вес ребра.
     *
     * @return вес ребра
     */
    public int weight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return weight == edge.weight 
            && Objects.equals(from, edge.from) 
            && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return from + " -> " + to + " (вес: " + weight + ")";
    }
}
