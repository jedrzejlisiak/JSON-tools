package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class purpose is to return XML made from JSON
 *
 * @author Lubomir Basinski
 */

public class JSONMinificationXML extends JSONDecorator{
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    /**
     *  This is a constructor calling constructor of component.
     *
     * @param comp is component
     */
    public JSONMinificationXML(JSONComponent comp) {
        super(comp);
    }
    /**
     *  This method calls the decorateMiniXml method on decorated component.
     *
     */
    @Override
    public String decorate() {
        return decorateMiniXml(comp.decorate());
    }
    /**
     *  This is MiniXml decoration method.
     *
     * @param s s is a JSON file and decorates it.
     * @return decorated JSON or error JSON.
     */
    public String decorateMiniXml(String s){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper xmlMapper = new XmlMapper();

        try {
            JsonNode tree = objectMapper.readTree(s);
            String jsonAsXml = xmlMapper.writer().withRootName("TransformedJson").writeValueAsString(tree);
            return jsonAsXml;
        }
        catch(JsonProcessingException r){
            r.printStackTrace();
            logger.debug("Error while processing JSON.");
            return "{ \"status\" : 500,\n" +
                    "\"developerMessage\" : \"Try again or with different file.\",\n"+
                    "\"userMessage\" : \"Internal Server Error, could not process JSON.\"}\n";
        }
    }
}
