package ru.nsu.dashkovskii;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестовый класс для проверки функциональности класса Mul.
 */
public class MulTest {

    /**
     * Тестирует умножение двух числовых констант.
     */
    @Test
    public void testEvaluateNumbers() {
        Mul mul = new Mul(new Number(3), new Number(4));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(12, mul.evaluate(vars));
    }

    /**
     * Тестирует умножение переменной на числовую константу.
     */
    @Test
    public void testEvaluateWithVariables() {
        Mul mul = new Mul(new Variable("x"), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 6);
        assertEquals(30, mul.evaluate(vars));
    }

    /**
     * Тестирует умножение двух переменных.
     */
    @Test
    public void testEvaluateTwoVariables() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 7);
        vars.put("y", 3);
        assertEquals(21, mul.evaluate(vars));
    }

    /**
     * Тестирует умножение на ноль.
     */
    @Test
    public void testEvaluateWithZero() {
        Mul mul = new Mul(new Number(0), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(0, mul.evaluate(vars));
    }

    /**
     * Тестирует взятие производной произведения (правило произведения).
     */
    @Test
    public void testDerivativeProductRule() {
        // d/dx(x * 3) = 1 * 3 + x * 0 = 3
        Mul mul = new Mul(new Variable("x"), new Number(3));
        Expression derivative = mul.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(3, derivative.evaluate(vars));
    }

    /**
     * Тестирует взятие производной произведения двух переменных.
     */
    @Test
    public void testDerivativeTwoVariables() {
        // d/dx(x * y) = 1 * y + x * 0 = y
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 7);
        assertEquals(7, derivative.evaluate(vars));
    }

    /**
     * Тестирует упрощение умножения.
     */
    @Test
    public void testSimplification() {
        // Умножение на 0 должно упрощаться до 0
        Mul mul1 = new Mul(new Variable("x"), new Number(0));
        Expression simplified1 = mul1.simplify();
        assertEquals("0", simplified1.toString());

        // Умножение на 1 должно упрощаться до переменной
        Mul mul2 = new Mul(new Variable("x"), new Number(1));
        Expression simplified2 = mul2.simplify();
        assertEquals("x", simplified2.toString());

        // Умножение констант должно вычисляться
        Mul mul3 = new Mul(new Number(2), new Number(3));
        Expression simplified3 = mul3.simplify();
        assertEquals("6", simplified3.toString());
    }

    /**
     * Тестирует строковое представление операции умножения.
     */
    @Test
    public void testToString() {
        Mul mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.toString());
    }
}
