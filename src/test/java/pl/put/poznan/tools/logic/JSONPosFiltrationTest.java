package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for JSONPosFiltration
 * @author Pawel Boruta
 */

class JSONPosFiltrationTest {

    @Test
    public void decoratePosTestWrongInput() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("Some wrong string to test");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{ \"status\" : 500,\n" +
                "\"developerMessage\" : \"Try again or with different file.\",\n"+
                "\"userMessage\" : \"Internal Server Error, could not process JSON.\"}\n", JPF.decorate());
    }

    @Test
    public void decoratePosTestEmpty() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{}", JPF.decorate());
    }

    @Test
    public void decoratePosTestOne() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":1,\"field2\":0}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{\"field1\":1}", JPF.decorate());
    }

    @Test
    public void decoratePosTestNested() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"fieldC\":{\"field1\":1},\"field2\":0}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{\"fieldC\":{\"field1\":1}}", JPF.decorate());
    }

    @Test
    public void decoratePosTestList() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("[{\"field1\":1},{\"field2\":0}]");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("[{\"field1\":1}]", JPF.decorate());
    }

    @Test
    public void decoratePosTestNestedList() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"fieldC\":[{\"field1\":1},{\"field2\":0}]}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{\"fieldC\":[{\"field1\":1}]}", JPF.decorate());
    }

    @Test
    public void decoratePosTestAll() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":1,\"field2\":0}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1,field2");
        assertEquals("{\"field1\":1,\"field2\":0}", JPF.decorate());
    }

    @Test
    public void decoratePosTestWithNesting() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":{\"field\":\"concat\"}},\"field2\":0}");
        JSONPosFiltration JPF = new JSONPosFiltration(jci, "field1");
        assertEquals("{\"field1\":{\"field\":\"concat\"}}", JPF.decorate());
    }
}