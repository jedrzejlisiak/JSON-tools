package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class JSONNegFilter extends JSONDecorator {
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    private final String[] fields;

    public JSONNegFilter(JSONComponent comp, String f) {super(comp); fields = f.split(",");}

    @Override
    public String decorate() { return decorateNeg(comp.decorate()); }

    public String decorateNeg(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"Error\": \"Couldn't filter Json\"}";
        }
        traverse(node);

        return(node.toString());
    }

    public void traverse(JsonNode node) {
        if(node.isObject()){
            Iterator<String> fieldNames = node.fieldNames();
            ArrayList<String> names = new ArrayList<String>();
            while (fieldNames.hasNext()) {
                names.add(fieldNames.next());
            }

            ObjectNode n = (ObjectNode) node;
            for(String fieldName : names) {

                if(Arrays.asList(fields).contains(fieldName)){
                    n.remove(fieldName);
                }
                else {
                    JsonNode fieldValue = node.get(fieldName);
                    traverse(fieldValue);
                }
            }


        } else if(node.isArray()){
            ArrayNode arrayNode = (ArrayNode) node;
            for(int i = 0; i < arrayNode.size(); i++) {
                    JsonNode arrayElement = arrayNode.get(i);
                    traverse(arrayElement);
            }

        }
    }
}