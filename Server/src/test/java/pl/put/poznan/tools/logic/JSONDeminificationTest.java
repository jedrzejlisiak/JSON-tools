package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for JSONDeminification
 * @author Pawel Boruta
 */

class JSONDeminificationTest {
    //Travis nie trawi testów
    @Disabled
    @Test
    public void undecorateMiniTestWrong() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("Some wrong string to test");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{ \"status\" : 500,\n" +
                "\"developerMessage\" : \"Try again or with different file.\",\n"+
                "\"userMessage\" : \"Internal Server Error, could not process JSON.\"}\n", JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestEmpty() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{}");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{ }",JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestSimple() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":\"value1\"}");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{\r\n  \"field1\" : \"value1\"\r\n}",JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestNested() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":{\"value1\":\"nested value\"}}");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{\r\n  \"field1\" : {\r\n    \"value1\" : \"nested value\"\r\n  }\r\n}",JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestDeminified() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\r\n  \"field1\" : {\r\n    \"value1\" : \"nested value\"\r\n  },\r\n" +
                "  \"field2\" : 5\r\n}");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{\r\n  \"field1\" : {\r\n    \"value1\" : \"nested value\"\r\n  },\r\n" +
                "  \"field2\" : 5\r\n}", JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestWithLists() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":[1,2,3,4,5],\"field2\":[{\"field3\":\"text\"},{\"field4\":4}]}");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("{\r\n  \"field1\" : [ 1, 2, 3, 4, 5 ],\r\n  " +
                "\"field2\" : [ {\r\n    \"field3\" : \"text\"\r\n  }, " +
                "{\r\n    \"field4\" : 4\r\n  } ]\r\n}", JD.decorate());
    }
    @Disabled
    @Test
    public void undecorateMiniTestSimpleList() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("[1,2,3,4,5,6,7,8,9,0]");
        JSONDeminification JD = new JSONDeminification(jci);
        assertEquals("[ 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 ]", JD.decorate());
    }
}