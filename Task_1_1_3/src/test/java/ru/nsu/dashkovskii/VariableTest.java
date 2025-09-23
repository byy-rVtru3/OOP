package ru.nsu.dashkovskii;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности класса Variable.
 */
public class VariableTest {

    /**
     * Тестирует вычисление значения переменной с одним символом.
     */
    @Test
    public void testEvaluate() {
        Variable var = new Variable("x");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 42);
        assertEquals(42, var.evaluate(vars));
    }

    /**
     * Тестирует вычисление значения переменной с несколькими символами.
     */
    @Test
    public void testEvaluateMultiCharName() {
        Variable var = new Variable("foo");
        Map<String, Integer> vars = new HashMap<>();
        vars.put("foo", 123);
        assertEquals(123, var.evaluate(vars));
    }

    /**
     * Тестирует исключение при отсутствии значения переменной в контексте.
     */
    @Test
    public void testEvaluateMissingVariable() {
        Variable var = new Variable("x");
        Map<String, Integer> vars = new HashMap<>();
        assertThrows(RuntimeException.class, () -> var.evaluate(vars));
    }

    /**
     * Тестирует взятие производной по той же переменной (должна быть равна 1).
     */
    @Test
    public void testDerivativeSameVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(1, derivative.evaluate(vars));
    }

    /**
     * Тестирует взятие производной по другой переменной (должна быть равна 0).
     */
    @Test
    public void testDerivativeDifferentVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.derivative("y");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 3);
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует строковое представление переменной.
     */
    @Test
    public void testToString() {
        Variable var = new Variable("x");
        assertEquals("x", var.toString());

        Variable multiChar = new Variable("foo");
        assertEquals("foo", multiChar.toString());
    }
}
