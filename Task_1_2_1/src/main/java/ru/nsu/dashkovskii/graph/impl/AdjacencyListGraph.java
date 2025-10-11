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
 * Реализация графа через список смежности.
 */
public class AdjacencyListGraph implements Graph {
    private final boolean isDirected;
    private final Map<Vertex, List<Edge>> adjacencyMap;
    private TopologicalSortStrategy sortStrategy;

    /**
     * Конструктор графа.
     *
     * @param isDirected является ли граф ориентированным
     */
    public AdjacencyListGraph(boolean isDirected) {
        this.isDirected = isDirected;
        this.adjacencyMap = new HashMap<>();
    }

    /**
     * Конструктор графа со стратегией сортировки.
     *
     * @param isDirected является ли граф ориентированным
     * @param sortStrategy стратегия топологической сортировки
     */
    public AdjacencyListGraph(boolean isDirected, TopologicalSortStrategy sortStrategy) {
        this.isDirected = isDirected;
        this.adjacencyMap = new HashMap<>();
        this.sortStrategy = sortStrategy;
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return;
        }
        adjacencyMap.put(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return;
        }

        // Удаляем вершину
        adjacencyMap.remove(vertex);

        // Удаляем все рёбра, ведущие к этой вершине
        for (List<Edge> edges : adjacencyMap.values()) {
            edges.removeIf(edge -> edge.to().equals(vertex));
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

        // Добавляем ребро
        adjacencyMap.get(from).add(edge);

        // Для неориентированного графа добавляем обратное ребро
        if (!isDirected) {
            // Проверяем, чтобы не добавить дубликат обратного ребра
            Edge reverseEdge = new Edge(to, from, edge.weight());
            if (!adjacencyMap.get(to).contains(reverseEdge)) {
                adjacencyMap.get(to).add(reverseEdge);
            }
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

        adjacencyMap.get(from).remove(edge);

        if (!isDirected) {
            adjacencyMap.get(to).removeIf(e ->
                    e.from().equals(to) && e.to().equals(from));
        }
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return new ArrayList<>();
        }

        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : adjacencyMap.get(vertex)) {
            neighbors.add(edge.to());
        }

        return neighbors;
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(adjacencyMap.keySet());
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> allEdges = new ArrayList<>();

        if (isDirected) {
            for (List<Edge> edges : adjacencyMap.values()) {
                allEdges.addAll(edges);
            }
        } else {
            // Для неориентированного графа возвращаем только уникальные рёбра
            java.util.Set<Edge> uniqueEdges = new java.util.HashSet<>();
            for (List<Edge> edges : adjacencyMap.values()) {
                for (Edge edge : edges) {
                    // Добавляем только если обратного ребра ещё нет
                    Edge reverseEdge = new Edge(edge.to(), edge.from(), edge.weight());
                    if (!uniqueEdges.contains(reverseEdge)) {
                        uniqueEdges.add(edge);
                    }
                }
            }
            allEdges.addAll(uniqueEdges);
        }

        return allEdges;
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return adjacencyMap.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        if (edge == null || !containsVertex(edge.from())) {
            return false;
        }
        return adjacencyMap.get(edge.from()).contains(edge);
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
        return adjacencyMap.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph (")
                .append(isDirected ? "ориентированный" : "неориентированный")
                .append("):\n");

        for (Map.Entry<Vertex, List<Edge>> entry : adjacencyMap.entrySet()) {
            sb.append(entry.getKey()).append(" -> ");
            List<Vertex> neighbors = new ArrayList<>();
            for (Edge edge : entry.getValue()) {
                neighbors.add(edge.to());
            }
            sb.append(neighbors).append("\n");
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
