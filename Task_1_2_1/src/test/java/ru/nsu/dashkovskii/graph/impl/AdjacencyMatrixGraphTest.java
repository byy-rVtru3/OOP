package ru.nsu.dashkovskii.graph.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;
import ru.nsu.dashkovskii.strategy.KhanTopologicalSort;

/**
 * Тесты для класса AdjacencyMatrixGraph.
 */
class AdjacencyMatrixGraphTest {

    private Graph graph;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixGraph(true);
        v1 = new Vertex("A");
        v2 = new Vertex("B");
        v3 = new Vertex("C");
    }

    @Test
    void testAddVertex() {
        graph.addVertex(v1);
        assertTrue(graph.containsVertex(v1));
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testAddDuplicateVertex() {
        graph.addVertex(v1);
        graph.addVertex(v1);
        assertEquals(1, graph.getVertexCount());
    }

    @Test
    void testRemoveVertex() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.removeVertex(v1);
        assertFalse(graph.containsVertex(v1));
        assertTrue(graph.containsVertex(v2));
    }

    @Test
    void testAddEdge() {
        Edge edge = new Edge(v1, v2, 5);
        graph.addEdge(edge);
        
        assertTrue(graph.containsVertex(v1));
        assertTrue(graph.containsVertex(v2));
        assertTrue(graph.containsEdge(edge));
    }

    @Test
    void testRemoveEdge() {
        Edge edge = new Edge(v1, v2, 5);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        
        assertFalse(graph.containsEdge(edge));
    }

    @Test
    void testGetNeighbors() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v1, v3));

        List<Vertex> neighbors = graph.getNeighbors(v1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(v2));
        assertTrue(neighbors.contains(v3));
    }

    @Test
    void testIsDirected() {
        assertTrue(graph.isDirected());
        
        Graph undirectedGraph = new AdjacencyMatrixGraph(false);
        assertFalse(undirectedGraph.isDirected());
    }

    @Test
    void testUndirectedGraphEdges() {
        Graph undirectedGraph = new AdjacencyMatrixGraph(false);
        undirectedGraph.addEdge(new Edge(v1, v2));

        assertTrue(undirectedGraph.getNeighbors(v1).contains(v2));
        assertTrue(undirectedGraph.getNeighbors(v2).contains(v1));
    }

    @Test
    void testRemoveVertexRemovesEdges() {
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v2, v3));
        
        graph.removeVertex(v2);
        
        assertFalse(graph.containsVertex(v2));
        assertEquals(0, graph.getNeighbors(v1).size());
    }

    @Test
    void testGetVertices() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        
        List<Vertex> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(v1));
        assertTrue(vertices.contains(v2));
    }

    @Test
    void testGetEdges() {
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        
        graph.addEdge(e1);
        graph.addEdge(e2);
        
        List<Edge> edges = graph.getEdges();
        assertEquals(2, edges.size());
    }

    @Test
    void testEqualsGraph() {
        final Graph graph2 = new AdjacencyListGraph(true);
        
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(new Edge(v1, v2));
        
        graph2.addVertex(v1);
        graph2.addVertex(v2);
        graph2.addEdge(new Edge(v1, v2));
        
        assertTrue(graph.equalsGraph(graph2));
    }

    @Test
    void testTopologicalSortWithStrategy() {
        graph = new AdjacencyMatrixGraph(true, new KhanTopologicalSort());
        graph.addVertex(new Vertex("1"));
        graph.addVertex(new Vertex("2"));
        graph.addVertex(new Vertex("3"));
        graph.addEdge(new Vertex("1"), new Vertex("2"));
        graph.addEdge(new Vertex("2"), new Vertex("3"));

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(3, sorted.size());
        assertTrue(sorted.indexOf(1) < sorted.indexOf(2));
        assertTrue(sorted.indexOf(2) < sorted.indexOf(3));
    }

    @Test
    void testTopologicalSortWithoutStrategy() {
        assertThrows(IllegalStateException.class, () -> graph.topologicalSort());
    }

    @Test
    void testMatrixExpansion() {
        for (int i = 0; i < 10; i++) {
            graph.addVertex(new Vertex("V" + i));
        }
        assertEquals(10, graph.getVertexCount());
    }

    @Test
    void testToString() {
        graph.addVertex(v1);
        String result = graph.toString();
        assertNotNull(result);
        assertTrue(result.contains("AdjacencyMatrixGraph"));
    }
}
