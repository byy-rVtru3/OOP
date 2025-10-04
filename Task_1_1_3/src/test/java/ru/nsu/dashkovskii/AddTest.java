package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности класса Add.
 */
public class AddTest {

    /**
     * Тестирует сложение двух числовых констант.
     */
    @Test
    public void testEvaluateNumbers() {
        Add add = new Add(new Number(3), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(8, add.evaluate(vars));
    }

    /**
     * Тестирует сложение переменной и числовой константы.
     */
    @Test
    public void testEvaluateWithVariables() {
        Add add = new Add(new Variable("x"), new Number(10));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(15, add.evaluate(vars));
    }

    /**
     * Тестирует сложение двух переменных.
     */
    @Test
    public void testEvaluateTwoVariables() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 7);
        vars.put("y", 3);
        assertEquals(10, add.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от суммы.
     */
    @Test
    public void testDerivative() {
        // (x + 5)' = 1 + 0
        Add add = new Add(new Variable("x"), new Number(5));
        Expression derivative = add.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(1, derivative.evaluate(vars));
    }

    /**
     * Тестирует взятие производной от суммы двух переменных.
     */
    @Test
    public void testDerivativeTwoVariables() {
        // (x + y)' по x = 1 + 0
        Add add = new Add(new Variable("x"), new Variable("y"));
        Expression derivative = add.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 3);
        assertEquals(1, derivative.evaluate(vars));
    }

    /**
     * Тестирует строковое представление операции сложения.
     */
    @Test
    public void testToString() {
        Add add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.toString());
    }

    /**
     * Тестирует упрощение сложения с нулем.
     */
    @Test
    public void testSimplificationWithZero() {
        Add add1 = new Add(new Variable("x"), new Number(0));
        Expression simplified1 = add1.simplify();
        assertEquals("x", simplified1.toString());

        Add add2 = new Add(new Number(0), new Variable("x"));
        Expression simplified2 = add2.simplify();
        assertEquals("x", simplified2.toString());
    }

    /**
     * Тестирует упрощение сложения двух констант.
     */
    @Test
    public void testSimplificationConstants() {
        Add add = new Add(new Number(3), new Number(7));
        Expression simplified = add.simplify();
        assertEquals("10", simplified.toString());
    }

    /**
     * Тестиру��т сложение с отрицательными числами.
     */
    @Test
    public void testEvaluateNegativeNumbers() {
        Add add = new Add(new Number(-5), new Number(3));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(-2, add.evaluate(vars));
    }

    /**
     * Тестиру��т производную константы.
     */
    @Test
    public void testDerivativeConstant() {
        Add add = new Add(new Number(5), new Number(3));
        Expression derivative = add.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует производную по переменной, которой нет в выражении.
     */
    @Test
    public void testDerivativeNonExistentVariable() {
        Add add = new Add(new Variable("x"), new Number(5));
        Expression derivative = add.derivative("y");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует сложение больших чисел.
     */
    @Test
    public void testEvaluateLargeNumbers() {
        Add add = new Add(new Number(1000000), new Number(2000000));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(3000000, add.evaluate(vars));
    }
}
