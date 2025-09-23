package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса Number.
 */
public class NumberTest {

    /**
     * Тестирует вычисление значения положительного числа.
     */
    @Test
    public void testEvaluate() {
        Number num = new Number(42);
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(42, num.evaluate(vars));
    }

    /**
     * Тестирует вычисление значения нуля.
     */
    @Test
    public void testEvaluateZero() {
        Number num = new Number(0);
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(0, num.evaluate(vars));
    }

    /**
     * Тестирует вычисление значения отрицательного числа.
     */
    @Test
    public void testEvaluateNegative() {
        Number num = new Number(-15);
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(-15, num.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от константы (должна быть равна нулю).
     */
    @Test
    public void testDerivative() {
        Number num = new Number(42);
        Expression derivative = num.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует строковое представление числа.
     */
    @Test
    public void testToString() {
        Number num = new Number(42);
        assertEquals("42", num.toString());

        Number negNum = new Number(-15);
        assertEquals("-15", negNum.toString());
    }
}
