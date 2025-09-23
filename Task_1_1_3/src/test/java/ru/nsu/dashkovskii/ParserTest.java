package ru.nsu.dashkovskii;

import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для проверки функциональности класса Parser.
 */
public class ParserTest {

    /**
     * Тестирует парсинг числовой константы.
     */
    @Test
    public void testParseNumber() {
        Expression expr = Parser.parse("42");
        assertEquals("42", expr.toString());
    }

    /**
     * Тестирует парсинг переменной.
     */
    @Test
    public void testParseVariable() {
        Expression expr = Parser.parse("x");
        assertEquals("x", expr.toString());
    }

    /**
     * Тестирует парсинг операции сложения.
     */
    @Test
    public void testParseAddition() {
        Expression expr = Parser.parse("(3+x)");
        assertEquals("(3+x)", expr.toString());
    }

    /**
     * Тестирует парсинг операции вычитания.
     */
    @Test
    public void testParseSubtraction() {
        Expression expr = Parser.parse("(x-5)");
        assertEquals("(x-5)", expr.toString());
    }

    /**
     * Тестирует парсинг операции умножения.
     */
    @Test
    public void testParseMultiplication() {
        Expression expr = Parser.parse("(2*x)");
        assertEquals("(2*x)", expr.toString());
    }

    /**
     * Тестирует парсинг операции деления.
     */
    @Test
    public void testParseDivision() {
        Expression expr = Parser.parse("(x/3)");
        assertEquals("(x/3)", expr.toString());
    }

    /**
     * Тестиру��т парсинг вложенного выражения.
     */
    @Test
    public void testParseNestedExpression() {
        Expression expr = Parser.parse("((x+1)*(y-2))");
        assertEquals("((x+1)*(y-2))", expr.toString());
    }

    /**
     * Тестирует парсинг пустой строки, должен выбрасывать исключение.
     */
    @Test
    public void testParseEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse(""));
    }

    /**
     * Тестирует парсинг некорректных скобок, должен выбрасывать исключение.
     */
    @Test
    public void testParseInvalidParentheses() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(x+"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("x+)"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("((x+y)"));
    }

    /**
     * Тестирует парсинг некорректного выражения, должен выбрасывать исключение.
     */
    @Test
    public void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("asdasd!@#"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("123abc"));
    }

    /**
     * Тестирует парсинг выражения с отсутствующим операндом.
     */
    @Test
    public void testParseMissingOperand() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(+x)"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(x+)"));
    }

    /**
     * Тестирует парсинг корректных присваиваний переменных.
     */
    @Test
    public void testParseAssignmentsValid() {
        Map<String, Integer> vars = Parser.parseAssignments("x = 10; y = 5");
        assertEquals(2, vars.size());
        assertEquals(10, vars.get("x"));
        assertEquals(5, vars.get("y"));
    }

    /**
     * Тестирует парсинг пустых присваиваний.
     */
    @Test
    public void testParseAssignmentsEmpty() {
        Map<String, Integer> vars = Parser.parseAssignments("");
        assertTrue(vars.isEmpty());

        vars = Parser.parseAssignments(null);
        assertTrue(vars.isEmpty());
    }

    /**
     * Тестирует парсинг одного присваивания.
     */
    @Test
    public void testParseAssignmentsSingle() {
        Map<String, Integer> vars = Parser.parseAssignments("x = 42");
        assertEquals(1, vars.size());
        assertEquals(42, vars.get("x"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным форматом.
     */
    @Test
    public void testParseAssignmentsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = = 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным именем переменной.
     */
    @Test
    public void testParseAssignmentsInvalidVariable() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("123 = 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x! = 10"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным значением переменной.
     */
    @Test
    public void testParseAssignmentsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = abc"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = 10.5"));
    }
}
