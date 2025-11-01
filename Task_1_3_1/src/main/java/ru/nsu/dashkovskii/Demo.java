package ru.nsu.dashkovskii;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Класс для демонстрации работы поиска подстроки согласно условию задачи.
 */
public class Demo {
    /**
     * Демонстрирует пример из условия задачи.
     *
     * @param args аргументы командной строки (не используются)
     * @throws IOException если возникла ошибка при работе с файлом
     */
    public static void main(String[] args) throws IOException {
        // Создаём тестовый файл из условия задачи
        createInputFile();

        // Вызываем функцию find как в условии задачи
        SubstringFinder finder = new SubstringFinder();
        List<Long> result = finder.find("input.txt", "бра");

        // Выводим результат
        System.out.println("Файл input.txt: абракадабра");
        System.out.println("Вход: find(\"input.txt\", \"бра\")");
        System.out.println("Выход: " + result);
    }

    private static void createInputFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("input.txt"), StandardCharsets.UTF_8))) {
            writer.write("абракадабра");
        }
    }
}

