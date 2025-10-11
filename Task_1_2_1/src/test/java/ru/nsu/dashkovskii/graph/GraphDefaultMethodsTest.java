package ru.nsu.dashkovskii.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.graph.impl.AdjacencyListGraph;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;

/**
 * Тесты для default методов интерфейса Graph.
 */
class GraphDefaultMethodsTest {

    private static final String TEST_FILE = "test_graph_default.txt";
    private Graph graph;
    private Vertex v1;
    private Vertex v2;
    private Vertex v3;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph(true);
        v1 = new Vertex("1");
        v2 = new Vertex("2");
        v3 = new Vertex("3");
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddEdgeWithVertices() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        
        graph.addEdge(v1, v2);
        
        assertTrue(graph.containsEdge(new Edge(v1, v2)));
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testRemoveEdgeWithVertices() {
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
        
        graph.removeEdge(v1, v2);
        
        assertFalse(graph.containsEdge(new Edge(v1, v2)));
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testGetVertexCount() {
        assertEquals(0, graph.getVertexCount());
        
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        
        assertEquals(3, graph.getVertexCount());
    }

    @Test
    void testGetEdgeCount() {
        assertEquals(0, graph.getEdgeCount());
        
        graph.addEdge(new Edge(v1, v2));
        graph.addEdge(new Edge(v2, v3));
        
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testReadFromFile() throws IOException {
        createTestFile("directed\n3\n1 2 3\n1 2\n2 3\n");
        
        graph.readFromFile(TEST_FILE);
        
        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testReadFromNonExistentFile() {
        assertThrows(IOException.class, () -> graph.readFromFile("nonexistent_file.txt"));
    }

    @Test
    void testEqualsGraphSameGraphs() {
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
    void testEqualsGraphDifferentVertices() {
        final Graph graph2 = new AdjacencyListGraph(true);
        
        graph.addVertex(v1);
        graph.addVertex(v2);
        
        graph2.addVertex(v1);
        graph2.addVertex(v3);
        
        assertFalse(graph.equalsGraph(graph2));
    }

    @Test
    void testEqualsGraphDifferentEdges() {
        final Graph graph2 = new AdjacencyListGraph(true);
        
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(new Edge(v1, v2));
        
        graph2.addVertex(v1);
        graph2.addVertex(v2);
        graph2.addEdge(new Edge(v2, v1));
        
        assertFalse(graph.equalsGraph(graph2));
    }

    @Test
    void testEqualsGraphDifferentDirectedness() {
        final Graph graph2 = new AdjacencyListGraph(false);
        
        graph.addVertex(v1);
        graph2.addVertex(v1);
        
        assertFalse(graph.equalsGraph(graph2));
    }

    @Test
    void testEqualsGraphNull() {
        assertFalse(graph.equalsGraph(null));
    }

    private void createTestFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write(content);
        }
    }
}
