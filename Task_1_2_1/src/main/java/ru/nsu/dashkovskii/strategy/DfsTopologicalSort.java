package ru.nsu.dashkovskii.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.model.Vertex;

/**
 * Топологическая сортировка методом обхода в глубину (DFS).
 */
public class DfsTopologicalSort implements TopologicalSortStrategy {

    @Override
    public List<Integer> sort(Graph graph) {
        List<Vertex> vertices = graph.getVertices();
        Set<Vertex> visited = new HashSet<>();
        Set<Vertex> recursionStack = new HashSet<>();
        List<Vertex> result = new ArrayList<>();

        for (Vertex vertex : vertices) {
            if (!visited.contains(vertex)) {
                if (dfs(vertex, graph, visited, recursionStack, result)) {
                    throw new IllegalStateException("Граф содержит цикл");
                }
            }
        }

        Collections.reverse(result);
        return result.stream()
                .map(v -> Integer.parseInt(v.name()))
                .toList();
    }

    private boolean dfs(Vertex vertex, Graph graph, Set<Vertex> visited,
                       Set<Vertex> recursionStack, List<Vertex> result) {
        visited.add(vertex);
        recursionStack.add(vertex);

        for (Vertex neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                if (dfs(neighbor, graph, visited, recursionStack, result)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                return true; // Цикл обнаружен
            }
        }

        recursionStack.remove(vertex);
        result.add(vertex);
        return false;
    }
}

