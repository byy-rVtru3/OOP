package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса Sub.
 */
public class SubTest {
    /**
     * Тестирует вычитание двух числовых констант.
     */
    @Test
    public void testEvaluateNumbers() {
        Sub sub = new Sub(new Number(10), new Number(3));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(7, sub.evaluate(vars));
    }

    /**
     * Тестирует вычитание числовой константы из переменной.
     */
    @Test
    public void testEvaluateWithVariables() {
        Sub sub = new Sub(new Variable("x"), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 12);
        assertEquals(7, sub.evaluate(vars));
    }

    /**
     * Тестирует вычитание двух переменных.
     */
    @Test
    public void testEvaluateTwoVariables() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 15);
        vars.put("y", 8);
        assertEquals(7, sub.evaluate(vars));
    }

    /**
     * Тестирует вычитание с отрицательным результатом.
     */
    @Test
    public void testEvaluateNegativeResult() {
        Sub sub = new Sub(new Number(3), new Number(10));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(-7, sub.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от разности.
     */
    @Test
    public void testDerivative() {
        // d/dx(x - 5) = 1 - 0 = 1
        Sub sub = new Sub(new Variable("x"), new Number(5));
        Expression derivative = sub.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(1, derivative.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от разности двух переменных.
     */
    @Test
    public void testDerivativeTwoVariables() {
        // d/dx(x - y) = 1 - 0 = 1
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Expression derivative = sub.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 3);
        assertEquals(1, derivative.evaluate(vars));
    }

    /**
     * Тестирует упрощение вычитания.
     */
    @Test
    public void testSimplification() {
        // Вычитание 0 должно упрощаться до левого операнда
        Sub sub1 = new Sub(new Variable("x"), new Number(0));
        Expression simplified1 = sub1.simplify();
        assertEquals("x", simplified1.toString());

        // x - x должно упрощаться до 0
        Sub sub2 = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified2 = sub2.simplify();
        assertEquals("0", simplified2.toString());

        // Вычитание констант должно вычисляться
        Sub sub3 = new Sub(new Number(10), new Number(3));
        Expression simplified3 = sub3.simplify();
        assertEquals("7", simplified3.toString());
    }

    /**
     * Тестирует строковое представление операции вычитания.
     */
    @Test
    public void testToString() {
        Sub sub = new Sub(new Variable("x"), new Number(3));
        assertEquals("(x-3)", sub.toString());
    }
}
