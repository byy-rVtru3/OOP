package ru.nsu.dashkovskii.reader;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.graph.impl.AdjacencyListGraph;
import ru.nsu.dashkovskii.model.Vertex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса GraphReader.
 */
class GraphReaderTest {

    private static final String TEST_FILE = "test_graph.txt";
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph(true);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testReadSimpleGraph() throws IOException {
        createTestFile("directed\n3\n1 2 3\n1 2\n2 3\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(3, graph.getVertexCount());
        assertTrue(graph.containsVertex(new Vertex("1")));
        assertTrue(graph.containsVertex(new Vertex("2")));
        assertTrue(graph.containsVertex(new Vertex("3")));
        assertEquals(2, graph.getEdgeCount());
    }

    @Test
    void testReadGraphWithWeights() throws IOException {
        createTestFile("directed\n2\n1 2\n1 2 5\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testReadGraphWithComments() throws IOException {
        createTestFile("# This is a comment\ndirected\n2\n1 2\n# Another comment\n1 2\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testReadGraphWithEmptyLines() throws IOException {
        createTestFile("directed\n\n2\n1 2\n\n1 2\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testReadEmptyGraph() throws IOException {
        createTestFile("directed\n0\n\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(0, graph.getVertexCount());
        assertEquals(0, graph.getEdgeCount());
    }

    @Test
    void testReadNonExistentFile() {
        assertThrows(IOException.class, () -> GraphReader.readGraph("nonexistent.txt", graph));
    }

    @Test
    void testReadGraphFromFile() throws IOException {
        createTestFile("directed\n4\n1 2 3 4\n1 2\n1 3\n2 4\n3 4\n");
        
        GraphReader.readGraph(TEST_FILE, graph);
        
        assertEquals(4, graph.getVertexCount());
        assertEquals(4, graph.getEdgeCount());
        assertEquals(2, graph.getNeighbors(new Vertex("1")).size());
    }

    private void createTestFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write(content);
        }
    }
}
