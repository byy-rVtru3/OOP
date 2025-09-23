package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class SubTest {

    @Test
    public void testEvaluateNumbers() {
        Sub sub = new Sub(new Number(10), new Number(3));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(7, sub.evaluate(vars));
    }

    @Test
    public void testEvaluateWithVariables() {
        Sub sub = new Sub(new Variable("x"), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 12);
        assertEquals(7, sub.evaluate(vars));
    }

    @Test
    public void testEvaluateTwoVariables() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 15);
        vars.put("y", 8);
        assertEquals(7, sub.evaluate(vars));
    }

    @Test
    public void testEvaluateNegativeResult() {
        Sub sub = new Sub(new Number(3), new Number(10));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(-7, sub.evaluate(vars));
    }

    @Test
    public void testDerivative() {
        // (x - 5)' = 1 - 0
        Sub sub = new Sub(new Variable("x"), new Number(5));
        Expression derivative = sub.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(1, derivative.evaluate(vars));
    }

    @Test
    public void testDerivativeTwoVariables() {
        // (x - y)' по x = 1 - 0
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Expression derivative = sub.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 3);
        assertEquals(1, derivative.evaluate(vars));
    }

    @Test
    public void testToString() {
        Sub sub = new Sub(new Variable("x"), new Number(3));
        assertEquals("(x-3)", sub.toString());
    }
}
