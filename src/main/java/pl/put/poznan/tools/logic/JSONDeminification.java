package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *  Class purpose is to deminify JSON file.
 *
 * @author Lubomir Basinski
 */
public class JSONDeminification extends JSONDecorator{
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    /**
     *  This is a constructor calling constructor of component.
     *
     * @param comp is component
     */
    public JSONDeminification(JSONComponent comp) {
        super(comp);
    }
    /**
     *  This function calls the undecorateMini function on decorated component.
     *
     */
    @Override
    public String decorate() {
        return undecorateMini(comp.decorate());
    }
    /**
     *  This is Deminification decoration function.
     *
     * @param s s is a JSON file and decorates it.
     * @return decorated JSON or error JSON.
     */
    private String undecorateMini(String s){
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(s);
            return node.toPrettyString();
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
