package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class BinaryOperationsTest {

    // Тестовая реализация BinaryOperations для проверки базового функционала
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
        protected String getSymbol() {
            return "*";
        }
    }

    @Test
    public void testToString() {
        BinaryOperations op = new TestBinaryOperation(new Number(3), new Variable("x"));
        assertEquals("(3*x)", op.toString());
    }

    @Test
    public void testToStringNested() {
        BinaryOperations inner = new TestBinaryOperation(new Number(2), new Variable("y"));
        BinaryOperations outer = new TestBinaryOperation(new Number(3), inner);
        assertEquals("(3*(2*y))", outer.toString());
    }

    @Test
    public void testConstructor() {
        Expression left = new Number(5);
        Expression right = new Variable("x");
        BinaryOperations op = new TestBinaryOperation(left, right);

        assertNotNull(op);
        assertEquals("(5*x)", op.toString());
    }
}
