package ru.nsu.dashkovskii;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для поиска всех вхождений подстроки в файле.
 * Использует буферизированный ввод для работы с большими файлами.
 */
public class SubstringFinder {
    private static final int BUFFER_SIZE = 8192;

    /**
     * Находит все вхождения подстроки в файле.
     *
     * @param filename путь к файлу
     * @param pattern  искомая подстрока
     * @return список индексов начала каждого вхождения
     * @throws IOException если возникла ошибка при чтении файла
     */
    public List<Long> find(String filename, String pattern) throws IOException {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Паттерн не может быть пустым");
        }

        List<Long> indices = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        long globalPosition = 0;

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(filename), BUFFER_SIZE);
             InputStreamReader reader = new InputStreamReader(bis, StandardCharsets.UTF_8)) {

            char[] chunk = new char[BUFFER_SIZE];
            int charsRead;

            while ((charsRead = reader.read(chunk)) != -1) {
                buffer.append(chunk, 0, charsRead);

                // Ищем все вхождения в текущем буфере
                int searchLimit = buffer.length() - pattern.length() + 1;
                int searchStart = Math.max(0, buffer.length() - charsRead - pattern.length() + 1);

                for (int i = searchStart; i < searchLimit; i++) {
                    if (matches(buffer, i, pattern)) {
                        long position = globalPosition + i;
                        if (indices.isEmpty() || indices.get(indices.size() - 1) != position) {
                            indices.add(position);
                        }
                    }
                }

                // Оставляем в буфере только последние (pattern.length - 1) символов
                if (buffer.length() >= pattern.length()) {
                    int keepSize = pattern.length() - 1;
                    globalPosition += buffer.length() - keepSize;
                    String overlap = buffer.substring(buffer.length() - keepSize);
                    buffer.setLength(0);
                    buffer.append(overlap);
                }
            }

            // Проверяем оставшуюся часть буфера
            for (int i = 0; i <= buffer.length() - pattern.length(); i++) {
                if (matches(buffer, i, pattern)) {
                    long position = globalPosition + i;
                    if (indices.isEmpty() || indices.get(indices.size() - 1) != position) {
                        indices.add(position);
                    }
                }
            }
        }

        return indices;
    }

    /**
     * Проверяет, совпадает ли подстрока в буфере с паттерном.
     *
     * @param buffer  буфер с текстом
     * @param start   начальная позиция в буфере
     * @param pattern искомый паттерн
     * @return true, если найдено совпадение
     */
    private boolean matches(StringBuilder buffer, int start, String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            if (buffer.charAt(start + i) != pattern.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}

