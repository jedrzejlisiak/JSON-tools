package pl.put.poznan.tools.logic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *  Test class for JSONMinification
 * @author Pawel Boruta
 */
class JSONMinificationTest {
    //Travis nie trawi testów
    @Disabled
    @Test
    public void decorateMiniTestWrong() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("Some wrong string to test");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{ \"status\" : 500,\n" +
                "\"developerMessage\" : \"Try again or with different file.\", \"userMessage\" : \"Internal Server Error, could not process JSON.\"}", JM.decorate());
    }
    @Disabled
    @Test
    public void decorateMiniTestMinified() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"Some\":\"already\",\"minified\":\"JSON\"}");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{\"Some\":\"already\",\"minified\":\"JSON\"}", JM.decorate());
    }
    @Disabled
    @Test
    public void decorateMiniTestWhiteSignInsideField() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\"Some white\":\"signs in already\",\" minified  \":\" JSON \"}");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{\"Some white\":\"signs in already\",\" minified  \":\" JSON \"}", JM.decorate());
    }
    @Disabled
    @Test
    public void decorateMiniTestEmpty() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{}");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{}", JM.decorate());
    }
    @Disabled
    @Test
    public void decorateMiniTestSimple() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("{\n\"field1\" : 0\n}");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{\"field1\":0}", JM.decorate());
    }
    @Disabled
    @Test
    public void decorateMiniTestWhiteSignsEverywhere() {
        JSONComponentImp jci = mock(JSONComponentImp.class);
        when(jci.decorate()).thenReturn("\n\t{\n    \t\" field1 \" : \n0\n, \" \"\t:   \n\t[\t5\n   ,\n2], \n\t \"field2\" : \"Some spaced text    \" \t\n\t}\t");
        JSONMinification JM = new JSONMinification(jci);
        assertEquals("{\" field1 \":0,\" \":[5,2],\"field2\":\"Some spaced text    \"}", JM.decorate());
    }
}