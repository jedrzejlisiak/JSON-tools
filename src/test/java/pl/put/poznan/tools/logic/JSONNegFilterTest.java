package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for JSONFilterTest
 * @author Pawel Boruta
 */
class JSONNegFilterTest {
    //Travis nie trawi testów
    @Disabled
    @Test
    public void decorateNegWrongInput() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("Some wrong string to test");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{\"status\": 500,\n" +
                "\"developerMessage\": \"Try again or with different file.\",\n"+
                "\"userMessage\": \"Internal Server Error, could not process JSON.\"}\n", JNF.decorate());
    }
    @Disabled
    @Test
    public void decorateNegTestEmpty() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{}", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestOne() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":1,\"field2\":0}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{\"field2\":0}", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestNested() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"fieldC\":{\"field1\":1},\"field2\":0}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{\"fieldC\":{},\"field2\":0}", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestList() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("[{\"field1\":1}, {\"field2\":0}]");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("[{},{\"field2\":0}]", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestNestedList() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"fieldC\":[{\"field1\":1},{\"field2\":0}]}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{\"fieldC\":[{},{\"field2\":0}]}", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestAll() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":1,\"field2\":0}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1,field2");
        assertEquals("{}", JNF.decorate());
    }
    @Disabled
    @Test
    public void decoratePosTestWithNesting() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"field1\":{\"field\":\"concat\"},\"field2\":0}");
        JSONNegFilter JNF = new JSONNegFilter(jci, "field1");
        assertEquals("{\"field2\":0}", JNF.decorate());
    }
}