package ru.nsu.dashkovskii.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ru.nsu.dashkovskii.graph.Graph;
import ru.nsu.dashkovskii.model.Edge;
import ru.nsu.dashkovskii.model.Vertex;

/**
 * Класс для чтения графа из файла.
 * Формат файла:
 * Строка 1: directed или undirected
 * Строка 2: количество вершин
 * Строка 3: имена вершин через пробел
 * Далее: рёбра в формате "from to [weight]"
 */
public class GraphReader {

    /**
     * Прочитать граф из файла.
     *
     * @param filename путь к файлу
     * @param graph    граф для заполнения
     * @throws IOException если возникла ошибка при чтении
     */
    public static void readGraph(String filename, Graph graph) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            int actualLineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                actualLineCount++;

                if (actualLineCount == 1) {
                    continue;
                } else if (actualLineCount == 2) {
                    continue;
                } else if (actualLineCount == 3) {
                    String[] vertexNames = line.split("\\s+");
                    for (String name : vertexNames) {
                        if (!name.isEmpty()) {
                            graph.addVertex(new Vertex(name));
                        }
                    }
                } else {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 2) {
                        Vertex from = new Vertex(parts[0]);
                        Vertex to = new Vertex(parts[1]);
                        int weight = 1;

                        if (parts.length >= 3 && isInteger(parts[2])) {
                            weight = Integer.parseInt(parts[2]);
                        }

                        graph.addEdge(new Edge(from, to, weight));
                    }
                }
            }
        }
    }

    /**
     * Проверить, является ли строка целым числом.
     *
     * @param str строка для проверки
     * @return true, если строка представляет целое число
     */
    private static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        int startIndex = 0;
        if (str.charAt(0) == '-' || str.charAt(0) == '+') {
            if (str.length() == 1) {
                return false;
            }
            startIndex = 1;
        }

        for (int i = startIndex; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
