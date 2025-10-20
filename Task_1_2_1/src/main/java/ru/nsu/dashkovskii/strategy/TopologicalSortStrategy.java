package ru.nsu.dashkovskii.strategy;

import java.util.List;
import ru.nsu.dashkovskii.graph.Graph;

/**
 * Интерфейс стратегии топологической сортировки.
 */
public interface TopologicalSortStrategy {
    /**
     * Выполнить топологическую сортировку графа.
     *
     * @param graph граф для сортировки
     * @return список отсортированных вершин
     * @throws IllegalStateException если граф содержит цикл
     */
    List<Integer> sort(Graph graph);
}
