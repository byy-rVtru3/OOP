package ru.nsu.dashkovskii;

import java.io.IOException;
import java.util.List;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.graph.impl.AdjacencyListGraph;
import ru.nsu.dashkovskii.graph.impl.AdjacencyMatrixGraph;
import ru.nsu.dashkovskii.graph.impl.IncidenceMatrixGraph;
import ru.nsu.dashkovskii.model.Vertex;
import ru.nsu.dashkovskii.reader.GraphReader;
import ru.nsu.dashkovskii.strategy.DfsTopologicalSort;
import ru.nsu.dashkovskii.strategy.KhanTopologicalSort;

/**
 * Главный класс для демонстрации работы с графом.
 */
public class Main {
    /**
     * Главный метод программы.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Graph graph = new AdjacencyMatrixGraph(true, new DfsTopologicalSort());

        try {
            GraphReader.readGraph("graph.txt", graph);
            System.out.println("Graph loaded from file");
        } catch (IOException e) {
            System.out.println("File not found, creating graph manually");
            createSampleGraph(graph);
        }

        System.out.println("Vertices: " + graph.getVertices());
        System.out.println("Vertex count: " + graph.getVertexCount());

        System.out.println(graph);

        try {
            List<Integer> sortedDfs = graph.topologicalSort();
            System.out.println("DFS Topological sort: " + sortedDfs);
        } catch (IllegalStateException e) {
            System.out.println("Graph contains cycle (DFS)");
        }

        graph.setTopologicalSortStrategy(new KhanTopologicalSort());
        try {
            List<Integer> sortedKhan = graph.topologicalSort();
            System.out.println("Khan Topological sort: " + sortedKhan);
        } catch (IllegalStateException e) {
            System.out.println("Graph contains cycle (Khan)");
        }

        demonstrateGraphImplementations();
    }

    /**
     * Создает образец графа.
     *
     * @param graph граф для заполнения
     */
    private static void createSampleGraph(Graph graph) {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addVertex(new Vertex("4"));

        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("1"), new Vertex("3"));
        graph.addEdge(new Vertex("2"), new Vertex("4"));
        graph.addEdge(new Vertex("3"), new Vertex("4"));
    }

    /**
     * Демонстрирует работу разных реализаций графов.
     */
    private static void demonstrateGraphImplementations() {
        System.out.println("\n=== Сравнение реализаций графов ===\n");

        Graph matrixGraph = new AdjacencyMatrixGraph(true, new DfsTopologicalSort());
        setupTestGraph(matrixGraph);
        System.out.println("Matrix Graph:");
        System.out.println(matrixGraph);

        Graph listGraph = new AdjacencyListGraph(true, new KhanTopologicalSort());
        setupTestGraph(listGraph);
        System.out.println("List Graph:");
        System.out.println(listGraph);

        Graph incidenceGraph = new IncidenceMatrixGraph(true, new DfsTopologicalSort());
        setupTestGraph(incidenceGraph);
        System.out.println("Incidence Graph:");
        System.out.println(incidenceGraph);

        System.out.println("Matrix equals List: " + matrixGraph.equals(listGraph));
        System.out.println("List equals Incidence: " + listGraph.equals(incidenceGraph));
    }

    /**
     * Настраивает тестовый граф.
     *
     * @param graph граф для настройки
     */
    private static void setupTestGraph(Graph graph) {
        graph.addVertex(new Vertex("0"));
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addEdge(new Vertex("0"), new Vertex("1"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
    }
}
