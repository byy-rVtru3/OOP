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
     * Тестирует взятие производной от частного x/2 по x.
     * Производная от x/2 по x равна 1/2, но результат вычисляется по формуле частного.
     */
    @Test
    public void testDerivative() {
        // d/dx(x/2) = (1*2 - x*0)/(2*2) = 2/4 = 0 (в целочисленной арифметике)
        Div div = new Div(new Variable("x"), new Number(2));
        Expression derivative = div.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        // Производная x/2 по формуле частного: (1*2 - x*0)/(2*2) = 2/4 = 0
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от частного двух переменных.
     */
    @Test
    public void testDerivativeTwoVariables() {
        // d/dx(x/y) = (1*y - x*0)/(y*y) = y/(y*y) = 1/y
        Div div = new Div(new Variable("x"), new Variable("y"));
        Expression derivative = div.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        vars.put("y", 5);
        // Результат: y/(y*y) = 5/25 = 0 (в целочисленной арифметике)
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует упрощение деления.
     */
    @Test
    public void testSimplification() {
        // Деление на 1 должно упрощаться до левого операнда
        Div div1 = new Div(new Variable("x"), new Number(1));
        Expression simplified1 = div1.simplify();
        assertEquals("x", simplified1.toString());

        // 0/x должно упрощаться до 0
        Div div2 = new Div(new Number(0), new Variable("x"));
        Expression simplified2 = div2.simplify();
        assertEquals("0", simplified2.toString());

        // x/x должно упрощаться до 1
        Div div3 = new Div(new Variable("x"), new Variable("x"));
        Expression simplified3 = div3.simplify();
        assertEquals("1", simplified3.toString());
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
