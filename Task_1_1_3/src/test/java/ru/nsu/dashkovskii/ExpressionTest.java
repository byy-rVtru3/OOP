package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

    /**
     * Тестирует дифференцирование выражений без переменной.
     */
    @Test
    public void testDerivativeConstantExpression() {
        Expression e = new Add(new Number(3), new Number(5));
        Expression derivative = e.derivative("x");
        
        java.util.Map<String, Integer> vars = new java.util.HashMap<>();
        assertEquals(0, derivative.evaluate(vars));
    }

    /**
     * Тестирует многократное дифференцирование.
     */
    @Test
    public void testMultipleDerivatives() {
        Expression e = new Variable("x");
        Expression de1 = e.derivative("x"); // 1
        Expression de2 = de1.derivative("x"); // 0

        java.util.Map<String, Integer> vars = new java.util.HashMap<>();
        vars.put("x", 5);
        assertEquals(1, de1.evaluate(vars));
        assertEquals(0, de2.evaluate(vars));
    }

    /**
     * Тестирует сложное выражение с несколькими переменными.
     */
    @Test
    public void testComplexExpressionEval() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2))
        );
        
        assertEquals(17, e.eval("x=3; y=4; z=10")); // (3*4 + 10/2) = 12 + 5 = 17
        assertEquals(4, e.eval("x=0; y=100; z=8")); // (0*100 + 8/2) = 0 + 4 = 4
    }

    /**
     * Тестирует переменные в разном порядке.
     */
    @Test
    public void testEvalVariablesInDifferentOrder() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2))
        );
        
        assertEquals(17, e.eval("z=10; x=3; y=4"));
    }

    /**
     * Тестирует лишние переменные.
     */
    @Test
    public void testEvalWithExtraVariables() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2))
        );
        
        assertEquals(17, e.eval("x=3; y=4; z=10; w=100"));
    }

    /**
     * Тестирует ошибку при отсутствии необходимой переменной.
     */
    @Test
    public void testEvalMissingVariable() {
        Expression e = new Add(
            new Mul(new Variable("x"), new Variable("y")),
            new Div(new Variable("z"), new Number(2))
        );
        
        assertThrows(RuntimeException.class, () -> e.eval("x=3; y=4"));
    }

    /**
     * Тестирует некорректное означивание - пустое значение.
     */
    @Test
    public void testEvalEmptyValue() {
        Expression e = new Variable("x");
        
        assertThrows(IllegalArgumentException.class, () -> e.eval("x=; y=5"));
    }

    /**
     * Тестирует некорректное означивание - не число.
     */
    @Test
    public void testEvalNonNumericValue() {
        Expression e = new Variable("x");
        
        assertThrows(IllegalArgumentException.class, () -> e.eval("x=abc"));
    }

    /**
     * Тестирует некорректное означивание - нет разделителя.
     */
    @Test
    public void testEvalNoSeparator() {
        Expression e = new Variable("x");
        
        assertThrows(IllegalArgumentException.class, () -> e.eval("x=5 y=3"));
    }

    /**
     * Тестирует парсинг с лишними пробелами и разделителями.
     */
    @Test
    public void testEvalWithExtraSpacesAndSeparators() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        
        assertEquals(7, e.eval(" x = 3 ; y = 4 ; ; "));
    }

    /**
     * Тестирует парсинг с нестандартными разделителями.
     */
    @Test
    public void testEvalWithCommaDelimiter() {
        Expression e = new Variable("x");
        
        assertThrows(IllegalArgumentException.class, () -> e.eval("x=3, y=4"));
    }
}
