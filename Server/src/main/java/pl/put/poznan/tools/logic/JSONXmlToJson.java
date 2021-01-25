package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class purpose is to return JSON made from XML
 *
 * @author Lubomir Basinski
 */

public class JSONXmlToJson extends JSONDecorator{
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    /**
     *  This is a constructor calling constructor of component.
     *
     * @param comp is component
     */
    public JSONXmlToJson(JSONComponent comp) {
        super(comp);
    }
    /**
     *  This method calls the decorateMini method on decorated component.
     *
     */
    @Override
    public String decorate() {
        return decorateToJson(comp.decorate());
    }
    /**
     *  This is conversion to json from xml decoration method.
     *
     * @param s s is a XML file and decorates it.
     * @return decorated JSON or error JSON.
     */
    private String decorateToJson(String s){
        XmlMapper xmlMapper = new XmlMapper();
        try {
            JsonNode node = xmlMapper.readTree(s.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String output = jsonMapper.writeValueAsString(node);
            return output;
        }
        catch (java.io.IOException e){
            return "{ \"status\" : 500,\n" +
                    "\"developerMessage\" : \"Try again or with different file.\", \"userMessage\" : \"Internal Server Error, could not process XML.\"}";
        }
    }
}
