package ru.nsu.dashkovskii;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки функциональности класса Div.
 */
public class DivTest {

    /**
     * Тестирует деление двух числовых констант.
     */
    @Test
    public void testEvaluateNumbers() {
        Div div = new Div(new Number(10), new Number(2));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(5, div.evaluate(vars));
    }

    /**
     * Тестирует деление переменной на числовую константу.
     */
    @Test
    public void testEvaluateWithVariables() {
        Div div = new Div(new Variable("x"), new Number(2));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 8);
        assertEquals(4, div.evaluate(vars));
    }

    /**
     * Тестирует деление двух переменных.
     */
    @Test
    public void testEvaluateTwoVariables() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 15);
        vars.put("y", 3);
        assertEquals(5, div.evaluate(vars));
    }

    /**
     * Тестирует деление на ноль, должно выбрасывать исключение.
     */
    @Test
    public void testDivisionByZero() {
        Div div = new Div(new Number(5), new Number(0));
        Map<String, Integer> vars = new HashMap<>();
        assertThrows(ArithmeticException.class, () -> div.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от частного.
     */
    @Test
    public void testDerivative() {
        // (x/2)' = (1*2 - x*0)/(2*2) = 2/4
        Div div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        // Производная x/2 по x должна быть 1/2, но так как работаем с int, получается 0
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует строковое представление операции деления.
     */
    @Test
    public void testToString() {
        Div div = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", div.toString());
    }
}
