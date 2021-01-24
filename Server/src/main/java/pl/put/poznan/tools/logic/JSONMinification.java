package pl.put.poznan.tools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class purpose is to minify JSON file.
 *
 * @author Jędrzej Lisiak
 */

public class JSONMinification extends JSONDecorator{
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    /**
     *  This is a constructor calling constructor of component.
     *
     * @param comp is component
     */
    public JSONMinification(JSONComponent comp) {
        super(comp);
    }
    /**
     *  This method calls the decorateMini method on decorated component.
     *
     */
    @Override
    public String decorate() {
        return decorateMini(comp.decorate());
    }
    /**
     *  This is Minification decoration method.
     *
     * @param s s is a JSON file and decorates it.
     * @return decorated JSON or error JSON.
     */
    private String decorateMini(String s){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(s);
        } catch (JsonProcessingException e) {
            logger.debug("Error while processing JSON.");
            e.printStackTrace();
            return "{ \"status\" : 500,\n" +
                    "\"developerMessage\" : \"Try again or with different file.\", \"userMessage\" : \"Internal Server Error, could not process JSON.\"}";
        }
        String nJson = node.toString();
        String output = "";
        boolean coma = false;
        for(int i = 0, n = nJson.length() ; i < n ; i++) {
            char c = nJson.charAt(i);
            if(c == '\"'){
                coma = !coma;
            }
            if(c == ' '&&!coma){
                continue;
            }
            else{
                output += c;
            }
        }
        return output;
    }
}
