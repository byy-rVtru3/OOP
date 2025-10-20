package ru.nsu.dashkovskii.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;

/**
 * Топологическая сортировка алгоритмом Кана.
 */
public class KhanTopologicalSort implements TopologicalSortStrategy {

    @Override
    public List<Integer> sort(Graph graph) {
        List<Vertex> vertices = graph.getVertices();
        Map<Vertex, Integer> inDegree = new HashMap<>();

        // Инициализация входящих степеней
        for (Vertex vertex : vertices) {
            inDegree.put(vertex, 0);
        }

        // Подсчет входящих степеней
        for (Edge edge : graph.getEdges()) {
            inDegree.put(edge.to(), inDegree.get(edge.to()) + 1);
        }

        // Добавляем вершины с нулевой степенью в очередь
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex vertex : vertices) {
            if (inDegree.get(vertex) == 0) {
                queue.add(vertex);
            }
        }

        List<Vertex> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            result.add(current);

            // Уменьшаем степень соседей
            for (Vertex neighbor : graph.getNeighbors(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Если не все вершины обработаны, значит есть цикл
        if (result.size() != vertices.size()) {
            throw new IllegalStateException("Граф содержит цикл");
        }

        return result.stream()
                .map(v -> Integer.parseInt(v.name()))
                .toList();
    }
}

