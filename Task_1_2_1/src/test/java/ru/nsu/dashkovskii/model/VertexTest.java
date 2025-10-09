package ru.nsu.dashkovskii.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Vertex.
 */
class VertexTest {

    @Test
    void testCreateVertex() {
        Vertex vertex = new Vertex("A");
        assertEquals("A", vertex.name());
    }

    @Test
    void testVertexWithEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Vertex(""));
    }

    @Test
    void testVertexWithNullNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Vertex(null));
    }

    @Test
    void testVertexWithWhitespaceNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Vertex("   "));
    }

    @Test
    void testVertexEquality() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("A");
        Vertex v3 = new Vertex("B");

        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }

    @Test
    void testVertexHashCode() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("A");

        assertEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void testVertexToString() {
        Vertex vertex = new Vertex("TestVertex");
        assertEquals("TestVertex", vertex.toString());
    }

    @Test
    void testVertexNotEqualToNull() {
        Vertex vertex = new Vertex("A");
        assertNotEquals(null, vertex);
    }

    @Test
    void testVertexNotEqualToDifferentType() {
        Vertex vertex = new Vertex("A");
        assertNotEquals("A", vertex);
    }
}
