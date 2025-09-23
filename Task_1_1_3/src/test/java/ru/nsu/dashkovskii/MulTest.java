package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MulTest {

    @Test
    public void testEvaluateNumbers() {
        Mul mul = new Mul(new Number(3), new Number(4));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(12, mul.evaluate(vars));
    }

    @Test
    public void testEvaluateWithVariables() {
        Mul mul = new Mul(new Variable("x"), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 6);
        assertEquals(30, mul.evaluate(vars));
    }

    @Test
    public void testEvaluateTwoVariables() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 7);
        vars.put("y", 3);
        assertEquals(21, mul.evaluate(vars));
    }

    @Test
    public void testEvaluateWithZero() {
        Mul mul = new Mul(new Number(0), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(0, mul.evaluate(vars));
    }

    @Test
    public void testDerivativeProductRule() {
        // (x * 3)' = 1*3 + x*0 = 3
        Mul mul = new Mul(new Variable("x"), new Number(3));
        Expression derivative = mul.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(3, derivative.evaluate(vars));
    }

    @Test
    public void testDerivativeTwoVariables() {
        // (x * y)' по x = 1*y + x*0 = y
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 7);
        assertEquals(7, derivative.evaluate(vars));
    }

    @Test
    public void testToString() {
        Mul mul = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", mul.toString());
    }
}
