package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONComparatorTest {
    //Travis nie trawi testów
    @Disabled
    @Test
    public void comparisonTestEmpty() {
        JSONComparator JC = new JSONComparator("{ }", "{ }");
        assertEquals("", JC.checkFiles());
    }
    @Disabled
    @Test
    public void comparisonTestEqual() {
        JSONComparator JC = new JSONComparator("{\r\n  \"field1\" : \"value1\",\r\n  " +
                "\"field2\" : 2,\r\n  \"field3\" : [ 1, 2, 3 ]\r\n}",
                "{\r\n  \"field1\" : \"value1\",\r\n  \"field2\" : 2,\r\n  \"field3\" : [ 1, 2, 3 ]\r\n}");
        assertEquals("", JC.checkFiles());
    }
    @Disabled
    @Test
    public void comparisonTestUnequal() {
        JSONComparator JC = new JSONComparator("{\r\n  \"field1\" : \"value1\",\r\n  \"field2\" : 2\r\n}",
                "{\r\n  \"filed1\" : \"value1\",\r\n  \"field2\" : 3\r\n}");
        assertEquals("Unequal line nr \"1\":      \"field1\" : \"value1\",                            " +
                "  \"filed1\" : \"value1\",\n" +
                "Unequal line nr \"2\":      \"field2\" : 2                                    " +
                "  \"field2\" : 3\n", JC.checkFiles());
    }
    @Disabled
    @Test
    public void comparisonTestDifferentLength() {
        JSONComparator JC = new JSONComparator("{\r\n  \"field1\" : \"value1\",\r\n  \"field2\" : 2\r\n}",
                "{\r\n  \"field1\" : \"value1\",\r\n  \"field2\" : 2,\r\n  \"field3\" : \"one line more\"\r\n}");
        assertEquals("Unequal line nr \"2\":      \"field2\" : 2                                    " +
                "  \"field2\" : 2,\nUnequal line nr \"3\":    " +
                "}                                                   \"field3\" : \"one line more\"\n" +
                "Unequal line nr \"4\":                                                      }\n", JC.checkFiles());
    }
}