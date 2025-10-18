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
 * Реализация графа через матрицу инцидентности.
 */
public class IncidenceMatrixGraph implements Graph {
    private final boolean isDirected;
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private final Map<Vertex, Integer> vertexIndices;
    private List<List<Integer>> matrix;
    private TopologicalSortStrategy sortStrategy;

    /**
     * Конструктор графа.
     *
     * @param isDirected является ли граф ориентированным
     */
    public IncidenceMatrixGraph(boolean isDirected) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.vertexIndices = new HashMap<>();
        this.matrix = new ArrayList<>();
    }

    /**
     * Конструктор графа со стратегией сортировки.
     *
     * @param isDirected является ли граф ориентированным
     * @param sortStrategy стратегия топологической сортировки
     */
    public IncidenceMatrixGraph(boolean isDirected, TopologicalSortStrategy sortStrategy) {
        this.isDirected = isDirected;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.vertexIndices = new HashMap<>();
        this.matrix = new ArrayList<>();
        this.sortStrategy = sortStrategy;
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return;
        }

        int oldVertexCount = vertices.size();
        vertices.add(vertex);
        vertexIndices.put(vertex, oldVertexCount);

        // Добавляем новую строку в матрицу (с нулями для всех существующих рёбер)
        List<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            newRow.add(0);
        }
        matrix.add(newRow);
    }

    @Override
    public void removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return;
        }

        // Удаляем все рёбра, связанные с этой вершиной
        List<Edge> edgesToRemove = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from().equals(vertex) || edge.to().equals(vertex)) {
                edgesToRemove.add(edge);
            }
        }

        for (Edge edge : edgesToRemove) {
            removeEdge(edge);
        }

        // Удаляем вершину
        int index = vertexIndices.get(vertex);
        vertices.remove(index);
        vertexIndices.remove(vertex);

        // Обновляем индексы
        for (int i = index; i < vertices.size(); i++) {
            vertexIndices.put(vertices.get(i), i);
        }

        // Удаляем строку из матрицы
        matrix.remove(index);
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

        edges.add(edge);

        // Добавляем новый столбец в каждую строку матрицы
        int fromIndex = vertexIndices.get(from);
        int toIndex = vertexIndices.get(to);

        for (int i = 0; i < vertices.size(); i++) {
            if (i == fromIndex) {
                if (isDirected) {
                    matrix.get(i).add(1);   // исходящее ребро
                } else {
                    matrix.get(i).add(1);
                }
            } else if (i == toIndex) {
                if (isDirected) {
                    matrix.get(i).add(-1);  // входящее ребро
                } else {
                    matrix.get(i).add(1);
                }
            } else {
                matrix.get(i).add(0);  // вершина не связана с этим ребром
            }
        }
    }

    @Override
    public void removeEdge(Edge edge) {
        if (edge == null) {
            return;
        }

        int edgeIndex = -1;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).equals(edge)) {
                edgeIndex = i;
                break;
            }
        }

        if (edgeIndex == -1) {
            return;
        }

        edges.remove(edgeIndex);

        // Удаляем столбец из всех строк матрицы
        for (List<Integer> row : matrix) {
            row.remove(edgeIndex);
        }
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();

        if (!containsVertex(vertex)) {
            return neighbors;
        }

        for (Edge edge : edges) {
            if (edge.from().equals(vertex)) {
                if (!neighbors.contains(edge.to())) {
                    neighbors.add(edge.to());
                }
            } else if (!isDirected && edge.to().equals(vertex)) {
                if (!neighbors.contains(edge.from())) {
                    neighbors.add(edge.from());
                }
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
        return new ArrayList<>(edges);
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return vertexIndices.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
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
        return vertices.hashCode() ^ edges.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IncidenceMatrixGraph (")
                .append(isDirected ? "ориентированный" : "неориентированный")
                .append("):\n");
        sb.append("Вершины: ").append(vertices).append("\n");
        sb.append("Рёбра: ").append(edges).append("\n");
        sb.append("Матрица инцидентности:\n");

        if (edges.isEmpty()) {
            sb.append("  (нет рёбер)\n");
            return sb.toString();
        }

        sb.append("      ");
        for (int j = 0; j < edges.size(); j++) {
            sb.append(String.format("e%-3d", j));
        }
        sb.append("\n");

        for (int i = 0; i < vertices.size(); i++) {
            sb.append(String.format("%-6s", vertices.get(i)));
            for (int j = 0; j < edges.size(); j++) {
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
