package ru.nsu.dashkovskii;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.dashkovskii.ast.Expression;
import ru.nsu.dashkovskii.parcer.Papcep;

/**
 * Тестовый класс для проверки функциональности класса TTapcep.
 */
public class ParserTest {

    private final Papcep parser = new Papcep();

    /**
     * Тестирует парсинг числовой константы.
     */
    @Test
    public void testParseNumber() {
        Expression expr = parser.parse("42");
        assertEquals("42", expr.toString());
    }

    /**
     * Тестирует парсинг переменной.
     */
    @Test
    public void testParseVariable() {
        Expression expr = parser.parse("x");
        assertEquals("x", expr.toString());
    }

    /**
     * Тестирует парсинг операции сложения.
     */
    @Test
    public void testParseAddition() {
        Expression expr = parser.parse("(3+x)");
        assertEquals("(3+x)", expr.toString());
    }

    /**
     * Тестирует парсинг операции вычитания.
     */
    @Test
    public void testParseSubtraction() {
        Expression expr = parser.parse("(x-5)");
        assertEquals("(x-5)", expr.toString());
    }

    /**
     * Тестирует парсинг операции умножения.
     */
    @Test
    public void testParseMultiplication() {
        Expression expr = parser.parse("(2*x)");
        assertEquals("(2*x)", expr.toString());
    }

    /**
     * Тестирует парсинг операции деления.
     */
    @Test
    public void testParseDivision() {
        Expression expr = parser.parse("(x/3)");
        assertEquals("(x/3)", expr.toString());
    }

    /**
     * Тестирует парсинг вложенного выражения.
     */
    @Test
    public void testParseNestedExpression() {
        Expression expr = parser.parse("((x+1)*(y-2))");
        assertEquals("((x+1)*(y-2))", expr.toString());
    }

    /**
     * Тестирует парсинг пустой строки, должен выбрасывать исключение.
     */
    @Test
    public void testParseEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    /**
     * Тестирует парсинг некорректных скобок, должен выбрасывать исключение.
     */
    @Test
    public void testParseInvalidParentheses() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(x+"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("x+)"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("((x+y)"));
    }

    /**
     * Тестирует парсинг некорректного выражения, должен выбрасывать исключение.
     */
    @Test
    public void testParseInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("asdasd!@#"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("123abc"));
    }

    /**
     * Тестирует парсинг выражения с отсутствующим операндом.
     */
    @Test
    public void testParseMissingOperand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(+x)"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("(x+)"));
    }

    /**
     * Тестирует парсинг корректных присваиваний переменных.
     */
    @Test
    public void testParseAssignmentsValid() {
        Map<String, Integer> vars = parser.parseAssignments("x = 10; y = 5");
        assertEquals(2, vars.size());
        assertEquals(10, vars.get("x"));
        assertEquals(5, vars.get("y"));
    }

    /**
     * Тестирует парсинг пустых присваиваний.
     */
    @Test
    public void testParseAssignmentsEmpty() {
        Map<String, Integer> vars = parser.parseAssignments("");
        assertTrue(vars.isEmpty());

        vars = parser.parseAssignments(null);
        assertTrue(vars.isEmpty());
    }

    /**
     * Тестирует парсинг одного присваивания.
     */
    @Test
    public void testParseAssignmentsSingle() {
        Map<String, Integer> vars = parser.parseAssignments("x = 42");
        assertEquals(1, vars.size());
        assertEquals(42, vars.get("x"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным форматом.
     */
    @Test
    public void testParseAssignmentsInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x 10"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x = = 10"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным именем переменной.
     */
    @Test
    public void testParseAssignmentsInvalidVariable() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("123 = 10"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x! = 10"));
    }

    /**
     * Тестирует парсинг присваиваний с некорректным значением переменной.
     */
    @Test
    public void testParseAssignmentsInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x = abc"));
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x = 10.5"));
    }

    /**
     * Тестирует парсинг присваиваний с пустым значением.
     */
    @Test
    public void testParseAssignmentsEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x="));
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x=; y=5"));
    }

    /**
     * Тестирует парсинг присваиваний с лишними пробелами и разделителями.
     */
    @Test
    public void testParseAssignmentsExtraSpacesAndSeparators() {
        Map<String, Integer> vars = parser.parseAssignments(" x = 3 ; y = 4 ; ; ");
        assertEquals(2, vars.size());
        assertEquals(3, vars.get("x"));
        assertEquals(4, vars.get("y"));
    }

    /**
     * Тестирует парсинг присваиваний с запятой вместо точки с запятой.
     */
    @Test
    public void testParseAssignmentsWithComma() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x=3, y=4"));
    }

    /**
     * Тестирует парсинг присваиваний без разделителя.
     */
    @Test
    public void testParseAssignmentsNoSeparator() {
        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments("x=5 y=3"));
    }

    /**
     * Тестирует парсинг присваиваний в разном порядке.
     */
    @Test
    public void testParseAssignmentsDifferentOrder() {
        Map<String, Integer> vars = parser.parseAssignments("z=10; x=3; y=4");
        assertEquals(3, vars.size());
        assertEquals(3, vars.get("x"));
        assertEquals(4, vars.get("y"));
        assertEquals(10, vars.get("z"));
    }

    /**
     * Тестирует парсинг присваиваний с лишними переменными.
     */
    @Test
    public void testParseAssignmentsExtraVariables() {
        Map<String, Integer> vars = parser.parseAssignments("x=3; y=4; z=10; w=100");
        assertEquals(4, vars.size());
        assertEquals(3, vars.get("x"));
        assertEquals(4, vars.get("y"));
        assertEquals(10, vars.get("z"));
        assertEquals(100, vars.get("w"));
    }
}
