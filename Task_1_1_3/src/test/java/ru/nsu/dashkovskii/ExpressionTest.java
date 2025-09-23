package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности абстрактного класса Expression.
 */
public class ExpressionTest {

    /**
     * Тестирует метод eval с одной переменной.
     */
    @Test
    public void testEval() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        assertEquals(15, expr.eval("x = 10"));
        assertEquals(7, expr.eval("x = 2"));
    }

    /**
     * Тестирует метод eval с несколькими переменными.
     */
    @Test
    public void testEvalMultipleVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(15, expr.eval("x = 10; y = 5"));
        assertEquals(7, expr.eval("x = 3; y = 4"));
    }

    /**
     * Тестирует метод eval с пустыми присваиваниями для константного выражения.
     */
    @Test
    public void testEvalEmptyAssignments() {
        Expression expr = new Number(42);
        assertEquals(42, expr.eval(""));
    }

    /**
     * Тестирует метод eval с null присваиваниями для константного выражения.
     */
    @Test
    public void testEvalNullAssignments() {
        Expression expr = new Number(42);
        // Если Parser.parseAssignments(null) корректно обрабатывает null
        assertDoesNotThrow(() -> expr.eval(null));
    }

    /**
     * Тестирует метод print, проверяет отсутствие исключений.
     */
    @Test
    public void testPrint() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        assertDoesNotThrow(() -> expr.print());
    }

    /**
     * Тестирует метод isConstant.
     */
    @Test
    public void testIsConstant() {
        Expression constant = new Number(42);
        assertTrue(constant.isConstant());

        Expression variable = new Variable("x");
        assertFalse(variable.isConstant());

        Expression expression = new Add(new Number(3), new Variable("x"));
        assertFalse(expression.isConstant());
    }

    /**
     * Тестирует метод isEqual.
     */
    @Test
    public void testIsEqual() {
        Expression expr1 = new Add(new Number(3), new Variable("x"));
        Expression expr2 = new Add(new Number(3), new Variable("x"));
        Expression expr3 = new Add(new Variable("x"), new Number(3));

        assertTrue(expr1.isEqual(expr2));
        assertFalse(expr1.isEqual(expr3)); // Порядок операндов важен
    }

    /**
     * Тестирует метод simplify.
     */
    @Test
    public void testSimplify() {
        // Тест упрощения x + 0 = x
        Expression expr = new Add(new Variable("x"), new Number(0));
        Expression simplified = expr.simplify();
        assertEquals("x", simplified.toString());
    }

    /**
     * Тестирует метод getVariables.
     */
    @Test
    public void testGetVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(2, expr.getVariables().size());
        assertTrue(expr.getVariables().contains("x"));
        assertTrue(expr.getVariables().contains("y"));

        Expression constant = new Number(42);
        assertTrue(constant.getVariables().isEmpty());
    }
}
