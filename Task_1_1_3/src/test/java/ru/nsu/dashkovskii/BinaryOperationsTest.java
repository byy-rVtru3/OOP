package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности абстрактного класса BinaryOperations.
 */
public class BinaryOperationsTest {

    private static class TestBinaryOperation extends BinaryOperations {
        public TestBinaryOperation(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        public int evaluate(Map<String, Integer> variables) {
            return left.evaluate(variables) * right.evaluate(variables);
        }

        @Override
        public Expression derivative(String var) {
            return new Number(0); // Упрощенная реализация для теста
        }

        @Override
        protected Expression simplifyInternal() {
            return this; // Упрощенная реализация для теста
        }

        @Override
        protected String getSymbol() {
            return "*";
        }
    }

    /**
     * Тестирует строковое представление бинарной операции.
     */
    @Test
    public void testToString() {
        BinaryOperations op = new TestBinaryOperation(new Number(3), new Variable("x"));
        assertEquals("(3*x)", op.toString());
    }

    /**
     * Тестирует строковое представление вложенных бинарных операций.
     */
    @Test
    public void testToStringNested() {
        BinaryOperations inner = new TestBinaryOperation(new Number(2), new Variable("y"));
        BinaryOperations outer = new TestBinaryOperation(new Number(3), inner);
        assertEquals("(3*(2*y))", outer.toString());
    }

    /**
     * Тестирует конструктор бинарной операции.
     */
    @Test
    public void testConstructor() {
        Expression left = new Number(5);
        Expression right = new Variable("x");
        BinaryOperations op = new TestBinaryOperation(left, right);

        assertNotNull(op);
        assertEquals("(5*x)", op.toString());
    }

    /**
     * Тестирует получение переменных из бинарной операции.
     */
    @Test
    public void testGetVariables() {
        BinaryOperations op = new TestBinaryOperation(new Variable("x"), new Variable("y"));
        Set<String> variables = op.getVariables();

        assertEquals(2, variables.size());
        assertTrue(variables.contains("x"));
        assertTrue(variables.contains("y"));
    }

    /**
     * Тестирует получение переменных из бинарной операции с константами.
     */
    @Test
    public void testGetVariablesWithConstants() {
        BinaryOperations op = new TestBinaryOperation(new Number(5), new Variable("x"));
        Set<String> variables = op.getVariables();

        assertEquals(1, variables.size());
        assertTrue(variables.contains("x"));
    }

    /**
     * Тестирует получение переменных из вложенных бинарных операций.
     */
    @Test
    public void testGetVariablesNested() {
        BinaryOperations inner = new TestBinaryOperation(new Variable("x"), new Variable("y"));
        BinaryOperations outer = new TestBinaryOperation(inner, new Variable("z"));
        Set<String> variables = outer.getVariables();

        assertEquals(3, variables.size());
        assertTrue(variables.contains("x"));
        assertTrue(variables.contains("y"));
        assertTrue(variables.contains("z"));
    }
}
