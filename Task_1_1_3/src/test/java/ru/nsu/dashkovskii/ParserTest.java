package ru.nsu.dashkovskii;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseNumber() {
        Expression expr = Parser.parse("42");
        assertEquals("42", expr.toString());
    }

    @Test
    public void testParseVariable() {
        Expression expr = Parser.parse("x");
        assertEquals("x", expr.toString());
    }

    @Test
    public void testParseAddition() {
        Expression expr = Parser.parse("(3+x)");
        assertEquals("(3+x)", expr.toString());
    }

    @Test
    public void testParseSubtraction() {
        Expression expr = Parser.parse("(x-5)");
        assertEquals("(x-5)", expr.toString());
    }

    @Test
    public void testParseMultiplication() {
        Expression expr = Parser.parse("(2*x)");
        assertEquals("(2*x)", expr.toString());
    }

    @Test
    public void testParseDivision() {
        Expression expr = Parser.parse("(x/3)");
        assertEquals("(x/3)", expr.toString());
    }

    @Test
    public void testParseNestedExpression() {
        Expression expr = Parser.parse("((x+1)*(y-2))");
        assertEquals("((x+1)*(y-2))", expr.toString());
    }

    @Test
    public void testParseEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse(""));
    }

    @Test
    public void testParseInvalidParentheses() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(x+"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("x+)"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("((x+y)"));
    }

    @Test
    public void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("asdasd!@#"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("123abc"));
    }

    @Test
    public void testParseMissingOperand() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(+x)"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parse("(x+)"));
    }

    @Test
    public void testParseAssignmentsValid() {
        Map<String, Integer> vars = Parser.parseAssignments("x = 10; y = 5");
        assertEquals(2, vars.size());
        assertEquals(10, vars.get("x"));
        assertEquals(5, vars.get("y"));
    }

    @Test
    public void testParseAssignmentsEmpty() {
        Map<String, Integer> vars = Parser.parseAssignments("");
        assertTrue(vars.isEmpty());

        vars = Parser.parseAssignments(null);
        assertTrue(vars.isEmpty());
    }

    @Test
    public void testParseAssignmentsSingle() {
        Map<String, Integer> vars = Parser.parseAssignments("x = 42");
        assertEquals(1, vars.size());
        assertEquals(42, vars.get("x"));
    }

    @Test
    public void testParseAssignmentsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = = 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x"));
    }

    @Test
    public void testParseAssignmentsInvalidVariable() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("123 = 10"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x! = 10"));
    }

    @Test
    public void testParseAssignmentsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = abc"));
        assertThrows(IllegalArgumentException.class, () -> Parser.parseAssignments("x = 10.5"));
    }
}
