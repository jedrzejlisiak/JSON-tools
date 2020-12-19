package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 *  Class purpose is to delete chosen fields from Json file
 */

public class JSONNegFilter extends JSONDecorator {
    private static final Logger logger = LoggerFactory.getLogger(JSONNegFilter.class);
    private final String[] fields;

    /**
     *  This is a constructor
     *
     * @param comp this is a component from decorator pattern, performing different operations on the Json file
     * @param f this is a string entered as a parameter of HTTP query, containing fields to be deleted
     */
    public JSONNegFilter(JSONComponent comp, String f) {super(comp); fields = f.split(",");}

    /**
     *  Override of main decorator pattern function.
     *  It passes result of decorate method of comp element from constructor to decorateNeg method
     */
    @Override
    public String decorate() { return decorateNeg(comp.decorate()); }

    /**
     *  This is a method performing filtering of Json file.
     *  It transforms file from String to JsonNode and passes it to traverse method
     *
     * @param json Json file processed by comp element
     */
    private String decorateNeg(String json) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"status\": 500,\n"+
            "\"developerMessage\": \"Try again or with different file.\",\n"+
                    "\"userMessage\": \"Internal Server Error, could not process JSON.\"}\n";
        }
        traverse(node);

        return(node.toString());
    }

    /**
     *  This is a method that performs DFS on the Json file tree structure, and deletes fields from fields array
     *
     * @param node JsonNode that is currently being analyzed
     */
    private void traverse(JsonNode node) {
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
