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
    private int[][] matrix;
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
        this.matrix = new int[0][0];
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
        this.matrix = new int[0][0];
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

        // Расширяем матрицу
        int newVertexCount = vertices.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[newVertexCount][edgeCount];

        for (int i = 0; i < oldVertexCount; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, edgeCount);
        }

        matrix = newMatrix;
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

        // Сжимаем матрицу
        int newVertexCount = vertices.size();
        int edgeCount = edges.size();
        int[][] newMatrix = new int[newVertexCount][edgeCount];

        int newRow = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i == index) {
                continue;
            }
            System.arraycopy(matrix[i], 0, newMatrix[newRow], 0, edgeCount);
            newRow++;
        }

        matrix = newMatrix;
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

        // Расширяем матрицу
        int vertexCount = vertices.size();
        int oldEdgeCount = edges.size() - 1;
        int newEdgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount][newEdgeCount];

        for (int i = 0; i < vertexCount; i++) {
            if (oldEdgeCount > 0) {
                System.arraycopy(matrix[i], 0, newMatrix[i], 0, oldEdgeCount);
            }
        }

        // Заполняем новый столбец
        int fromIndex = vertexIndices.get(from);
        int toIndex = vertexIndices.get(to);
        int edgeIndex = newEdgeCount - 1;

        if (isDirected) {
            newMatrix[fromIndex][edgeIndex] = 1;   // исходящее
            newMatrix[toIndex][edgeIndex] = -1;    // входящее
        } else {
            newMatrix[fromIndex][edgeIndex] = 1;
            newMatrix[toIndex][edgeIndex] = 1;
        }

        matrix = newMatrix;
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

        // Сжимаем матрицу
        int vertexCount = vertices.size();
        int newEdgeCount = edges.size();
        int[][] newMatrix = new int[vertexCount][newEdgeCount];

        for (int i = 0; i < vertexCount; i++) {
            int newCol = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == edgeIndex) {
                    continue;
                }
                newMatrix[i][newCol] = matrix[i][j];
                newCol++;
            }
        }

        matrix = newMatrix;
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
                sb.append(String.format("%-4d", matrix[i][j]));
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
