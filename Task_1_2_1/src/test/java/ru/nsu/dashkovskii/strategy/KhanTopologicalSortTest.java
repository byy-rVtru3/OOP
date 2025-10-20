package ru.nsu.dashkovskii.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.graph.impl.AdjacencyListGraph;
import ru.nsu.dashkovskii.model.Vertex;

/**
 * Тесты для класса KhanTopologicalSort.
 */
class KhanTopologicalSortTest {

    private TopologicalSortStrategy strategy;
    private Graph graph;

    @BeforeEach
    void setUp() {
        strategy = new KhanTopologicalSort();
        graph = new AdjacencyListGraph(true);
    }

    @Test
    void testSimpleTopologicalSort() {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("2"), new Vertex("3"));

        List<Integer> sorted = strategy.sort(graph);
        
        assertEquals(3, sorted.size());
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(2) < sorted.indexOf(3));
    }

    @Test
    void testComplexTopologicalSort() {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addVertex(new Vertex("4"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("1"), new Vertex("3"));
        graph.addEdge(new Vertex("2"), new Vertex("4"));
        graph.addEdge(new Vertex("3"), new Vertex("4"));

        List<Integer> sorted = strategy.sort(graph);
        
        assertEquals(4, sorted.size());
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(1) < sorted.indexOf(3));
        assertTrue(sorted.indexOf(2) < sorted.indexOf(4));
        assertTrue(sorted.indexOf(3) < sorted.indexOf(4));
    }

    @Test
    void testCyclicGraphThrowsException() {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("2"), new Vertex("3"));
        graph.addEdge(new Vertex("3"), new Vertex("1")); // Цикл

        assertThrows(IllegalStateException.class, () -> strategy.sort(graph));
    }

    @Test
    void testEmptyGraph() {
        List<Integer> sorted = strategy.sort(graph);
        assertTrue(sorted.isEmpty());
    }

    @Test
    void testSingleVertex() {
        graph.addVertex(new Vertex("1"));
        
        List<Integer> sorted = strategy.sort(graph);
        
        assertEquals(1, sorted.size());
        assertEquals(1, sorted.get(0));
    }

    @Test
    void testDisconnectedGraph() {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addVertex(new Vertex("4"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("3"), new Vertex("4"));

        List<Integer> sorted = strategy.sort(graph);
        
        assertEquals(4, sorted.size());
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(3) < sorted.indexOf(4));
    }

    @Test
    void testSelfLoopThrowsException() {
        graph.addVertex(new Vertex("1"));
        graph.addEdge(new Vertex("1"), new Vertex("1"));

        assertThrows(IllegalStateException.class, () -> strategy.sort(graph));
    }

    @Test
    void testMultipleStartVertices() {
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addVertex(new Vertex("4"));
        graph.addEdge(new Vertex("1"), new Vertex("3"));
        graph.addEdge(new Vertex("2"), new Vertex("4"));

        List<Integer> sorted = strategy.sort(graph);
        
        assertEquals(4, sorted.size());
    }
}
