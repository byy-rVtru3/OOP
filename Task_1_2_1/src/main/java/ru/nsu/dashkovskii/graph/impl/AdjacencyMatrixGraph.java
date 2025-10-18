package ru.nsu.dashkovskii.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;
import ru.nsu.dashkovskii.strategy.TopologicalSortStrategy;

/**
 * Реализация графа через матрицу смежности.
 */
public class AdjacencyMatrixGraph implements Graph {
    private final boolean isDirected;
    private final List<Vertex> vertices;
    private final Map<Vertex, Integer> vertexIndices;
    private List<List<Integer>> matrix;
    private TopologicalSortStrategy sortStrategy;

    /**
     * Конструктор графа.
     *
     * @param isDirected является ли граф ориентированным
     */
    public AdjacencyMatrixGraph(boolean isDirected) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
        this.vertexIndices = new HashMap<>();
        this.matrix = new ArrayList<>();
    }

    /**
     * Конструктор графа со стратегией сортировки.
     *
     * @param isDirected является ли граф ориентированным
     * @param sortStrategy стратегия топологической сортировки
     */
    public AdjacencyMatrixGraph(boolean isDirected, TopologicalSortStrategy sortStrategy) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
        this.vertexIndices = new HashMap<>();
        this.matrix = new ArrayList<>();
        this.sortStrategy = sortStrategy;
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return;
        }

        int oldSize = vertices.size();
        vertices.add(vertex);
        vertexIndices.put(vertex, oldSize);

        // Добавляем новую строку в матрицу
        List<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            newRow.add(0);
        }
        matrix.add(newRow);

        // Расширяем существующие строки на один столбец
        for (int i = 0; i < oldSize; i++) {
            matrix.get(i).add(0);
        }
    }

    @Override
    public void removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return;
        }

        int index = vertexIndices.get(vertex);
        vertices.remove(index);
        vertexIndices.remove(vertex);

        // Обновляем индексы
        for (int i = index; i < vertices.size(); i++) {
            vertexIndices.put(vertices.get(i), i);
        }

        // Удаляем строку из матрицы
        matrix.remove(index);

        // Удаляем столбец из всех строк
        for (List<Integer> row : matrix) {
            row.remove(index);
        }
    }

    @Override
    public void addEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        Vertex from = edge.from();
        Vertex to = edge.to();

        if (!containsVertex(from)) {
            addVertex(from);
        }
        if (!containsVertex(to)) {
            addVertex(to);
        }

        int fromIndex = vertexIndices.get(from);
        int toIndex = vertexIndices.get(to);

        matrix.get(fromIndex).set(toIndex, edge.weight());

        if (!isDirected) {
            matrix.get(toIndex).set(fromIndex, edge.weight());
        }
    }

    @Override
    public void removeEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        Vertex from = edge.from();
        Vertex to = edge.to();

        if (!containsVertex(from) || !containsVertex(to)) {
            return;
        }

        int fromIndex = vertexIndices.get(from);
        int toIndex = vertexIndices.get(to);

        matrix.get(fromIndex).set(toIndex, 0);

        if (!isDirected) {
            matrix.get(toIndex).set(fromIndex, 0);
        }
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();

        if (!containsVertex(vertex)) {
            return neighbors;
        }

        int index = vertexIndices.get(vertex);

        for (int i = 0; i < vertices.size(); i++) {
            if (matrix.get(index).get(i) != 0) {
                neighbors.add(vertices.get(i));
            }
        }

        return neighbors;
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (matrix.get(i).get(j) != 0) {
                    if (isDirected || i <= j) {
                        edges.add(new Edge(vertices.get(i), vertices.get(j), matrix.get(i).get(j)));
                    }
                }
            }
        }

        return edges;
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        if (edge == null || !containsVertex(edge.from())
                || !containsVertex(edge.to())) {
            return false;
        }

        int fromIndex = vertexIndices.get(edge.from());
        int toIndex = vertexIndices.get(edge.to());

        return matrix.get(fromIndex).get(toIndex) != 0;
    }

    @Override
    public boolean isDirected() {
        return isDirected;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Graph)) {
            return false;
        }
        return equalsGraph((Graph) obj);
    }

    @Override
    public int hashCode() {
        return vertices.hashCode() ^ getEdges().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrixGraph (")
                .append(isDirected ? "ориентированный" : "неориентированный")
                .append("):\n");
        sb.append("Вершины: ").append(vertices).append("\n");
        sb.append("Матрица смежности:\n");

        sb.append("    ");
        for (Vertex v : vertices) {
            sb.append(String.format("%-4s", v));
        }
        sb.append("\n");

        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%-4s", vertices.get(i)));
            for (int j = 0; j < vertices.size(); j++) {
                sb.append(String.format("%-4d", matrix.get(i).get(j)));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public void setTopologicalSortStrategy(TopologicalSortStrategy strategy) {
        this.sortStrategy = strategy;
    }

    @Override
    public List<Integer> topologicalSort() {
        if (sortStrategy == null) {
            throw new IllegalStateException("Стратегия топологической сортировки не установлена");
        }
        return sortStrategy.sort(this);
    }
}
