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
    private final Map<Vertex, List<Edge>> adjacencyList;
    private TopologicalSortStrategy sortStrategy;

    /**
     * Конструктор графа.
     *
     * @param isDirected является ли граф ориентированным
     */
    public AdjacencyListGraph(boolean isDirected) {
        this.isDirected = isDirected;
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Конструктор графа со стратегией сортировки.
     *
     * @param isDirected является ли граф ориентированным
     * @param sortStrategy стратегия топологической сортировки
     */
    public AdjacencyListGraph(boolean isDirected, TopologicalSortStrategy sortStrategy) {
        this.isDirected = isDirected;
        this.adjacencyList = new HashMap<>();
        this.sortStrategy = sortStrategy;
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null || containsVertex(vertex)) {
            return;
        }
        adjacencyList.put(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) {
            return;
        }

        // Удаляем вершину
        adjacencyList.remove(vertex);

        // Удаляем все рёбра, ведущие к этой вершине
        for (List<Edge> edges : adjacencyList.values()) {
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
        adjacencyList.get(from).add(edge);

        // Для неориентированного графа добавляем обратное ребро
        if (!isDirected) {
            adjacencyList.get(to).add(new Edge(to, from, edge.weight()));
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

        adjacencyList.get(from).remove(edge);

        if (!isDirected) {
            adjacencyList.get(to).removeIf(e ->
                    e.from().equals(to) && e.to().equals(from));
        }
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();

        if (!containsVertex(vertex)) {
            return neighbors;
        }

        for (Edge edge : adjacencyList.get(vertex)) {
            neighbors.add(edge.to());
        }

        return neighbors;
    }

    @Override
    public List<Vertex> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    @Override
    public List<Edge> getEdges() {
        List<Edge> allEdges = new ArrayList<>();

        for (List<Edge> edges : adjacencyList.values()) {
            allEdges.addAll(edges);
        }

        // Для неориентированного графа удаляем дубликаты
        if (!isDirected) {
            List<Edge> uniqueEdges = new ArrayList<>();
            for (Edge edge : allEdges) {
                boolean isDuplicate = false;
                for (Edge uniqueEdge : uniqueEdges) {
                    if ((uniqueEdge.from().equals(edge.from())
                            && uniqueEdge.to().equals(edge.to()))
                            || (uniqueEdge.from().equals(edge.to())
                            && uniqueEdge.to().equals(edge.from()))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    uniqueEdges.add(edge);
                }
            }
            return uniqueEdges;
        }

        return allEdges;
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean containsEdge(Edge edge) {
        if (edge == null || !containsVertex(edge.from())) {
            return false;
        }
        return adjacencyList.get(edge.from()).contains(edge);
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
        return adjacencyList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph (")
                .append(isDirected ? "ориентированный" : "неориентированный")
                .append("):\n");

        for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
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
