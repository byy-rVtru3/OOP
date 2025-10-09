package ru.nsu.dashkovskii.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Edge.
 */
class EdgeTest {

    @Test
    void testCreateEdgeWithWeight() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 5);

        assertEquals(v1, edge.from());
        assertEquals(v2, edge.to());
        assertEquals(5, edge.weight());
    }

    @Test
    void testCreateEdgeWithoutWeight() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2);

        assertEquals(v1, edge.from());
        assertEquals(v2, edge.to());
        assertEquals(1, edge.weight());
    }

    @Test
    void testEdgeWithNullFromThrowsException() {
        Vertex v2 = new Vertex("B");
        assertThrows(IllegalArgumentException.class, () -> new Edge(null, v2, 5));
    }

    @Test
    void testEdgeWithNullToThrowsException() {
        Vertex v1 = new Vertex("A");
        assertThrows(IllegalArgumentException.class, () -> new Edge(v1, null, 5));
    }

    @Test
    void testEdgeEquality() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge e1 = new Edge(v1, v2, 5);
        Edge e2 = new Edge(v1, v2, 5);
        Edge e3 = new Edge(v1, v2, 3);

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
    }

    @Test
    void testEdgeToString() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 5);

        String expected = "A -> B (вес: 5)";
        assertEquals(expected, edge.toString());
    }

    @Test
    void testEdgeWithZeroWeight() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 0);

        assertEquals(0, edge.weight());
    }

    @Test
    void testEdgeWithNegativeWeight() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, -5);

        assertEquals(-5, edge.weight());
    }

    @Test
    void testEdgeHashCode() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge e1 = new Edge(v1, v2, 5);
        Edge e2 = new Edge(v1, v2, 5);

        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testEdgeEqualsSameInstance() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 5);

        assertEquals(edge, edge);
    }

    @Test
    void testEdgeNotEqualToNull() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 5);

        assertNotEquals(null, edge);
    }

    @Test
    void testEdgeNotEqualToDifferentType() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge edge = new Edge(v1, v2, 5);

        assertNotEquals("edge", edge);
    }
}
