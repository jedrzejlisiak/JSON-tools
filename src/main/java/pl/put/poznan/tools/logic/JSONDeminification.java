package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONDeminification extends JSONDecorator{
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    public JSONDeminification(JSONComponent comp) {
        super(comp);
    }
    @Override
    public String decorate() {
        return undecorateMini(comp.decorate());
    }
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
                    "\"developerMessage\" : \"Try again or with different file.\", \"userMessage\" : \"Internal Server Error, could not process JSON.\"}";
        }
    }
}
