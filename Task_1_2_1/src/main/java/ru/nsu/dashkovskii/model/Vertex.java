package ru.nsu.dashkovskii.model;

import java.util.Objects;

/**
 * Класс, представляющий вершину графа.
 */
public class Vertex {
    private final String name;

    /**
     * Конструктор вершины.
     *
     * @param name имя вершины
     */
    public Vertex(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя вершины не может быть пустым");
        }
        this.name = name;
    }

    /**
     * Получить имя вершины.
     *
     * @return имя вершины
     */
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
