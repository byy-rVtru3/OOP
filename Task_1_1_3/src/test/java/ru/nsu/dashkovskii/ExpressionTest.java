package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionTest {

    @Test
    public void testEval() {
        Expression expr = new Add(new Variable("x"), new Number(5));
        assertEquals(15, expr.eval("x = 10"));
        assertEquals(7, expr.eval("x = 2"));
    }

    @Test
    public void testEvalMultipleVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(15, expr.eval("x = 10; y = 5"));
        assertEquals(7, expr.eval("x = 3; y = 4"));
    }

    @Test
    public void testEvalEmptyAssignments() {
        Expression expr = new Number(42);
        assertEquals(42, expr.eval(""));
        assertEquals(42, expr.eval(null));
    }

    @Test
    public void testPrint() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        assertDoesNotThrow(() -> expr.print());
    }
}
