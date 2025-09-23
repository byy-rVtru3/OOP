package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class AddTest {

    @Test
    public void testEvaluateNumbers() {
        Add add = new Add(new Number(3), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(8, add.evaluate(vars));
    }

    @Test
    public void testEvaluateWithVariables() {
        Add add = new Add(new Variable("x"), new Number(10));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 5);
        assertEquals(15, add.evaluate(vars));
    }

    @Test
    public void testEvaluateTwoVariables() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 7);
        vars.put("y", 3);
        assertEquals(10, add.evaluate(vars));
    }

    @Test
    public void testDerivative() {
        // (x + 5)' = 1 + 0
        Add add = new Add(new Variable("x"), new Number(5));
        Expression derivative = add.derivative("x");

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(1, derivative.evaluate(vars));
    }

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

    @Test
    public void testToString() {
        Add add = new Add(new Number(3), new Variable("x"));
        assertEquals("(3+x)", add.toString());
    }
}
