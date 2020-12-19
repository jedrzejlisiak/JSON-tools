package main.java.pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * This class overloads JSONDecorator and it's purpose is to extract given fields from JSON file
 */
public class JSONPosFiltration extends JSONDecorator {
    /**
     * This ArrayList stores fields that will be extracted from JSON
     */
    private final ArrayList<String> fields;

    /**
     * Class constructor splits String with fields and calls superclass constructor
     * @param comp interface implemented by superclass
     * @param f contains fields separated by commas
     */
    public JSONPosFiltration(JSONComponent comp, String f) {
        super(comp);
        String[] tmpfields = f.split(",");
        fields = new ArrayList<>(Arrays.asList(tmpfields));
    }

    /**
     * Overloaded function used to return processed JSON file in String form
     * @return returns value provided by DecoratePos function
     */
    @Override
    public String decorate() {
        return DecoratePos(comp.decorate());
    }

    /**
     * This function builds tree from JSON and after extracting needed values converts it back into String
     * @param s JSON file in String form
     * @return JSON with given fields and preserved structure of the provided JSON
     */
    private String DecoratePos(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode Json = objectMapper.createObjectNode();
        try {
            Json = objectMapper.readTree(s);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        traverse(Json);
        return Json.toString();
    }

    /**
     * Recursive function used to explore JSON graph
     * @param root Graph node that will be explored by function
     * @return Indicates if field from class variable fields was found during search in one of the node children
     */
    private boolean traverse(JsonNode root){
        boolean f = false;
        if(root.isObject()){
            Iterator<String> fieldNames = root.fieldNames();
            while(fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if(fields.contains(fieldName))
                {
                    f = true;
                    continue;
                }
                JsonNode fieldValue = root.get(fieldName);
                if(!traverse(fieldValue)) {
                    fieldNames.remove();
                }
                else { f = true; }
            }
            return f;

        } else if(root.isArray()){
            ArrayList<JsonNode> tmpJ = new ArrayList<>();
            ArrayNode arrayNode = (ArrayNode) root;
            for(int i = 0; i < arrayNode.size(); i++) {
                JsonNode arrayElement = arrayNode.get(i);
                if(traverse(arrayElement)){
                    tmpJ.add(arrayElement);
                }
            }
            if(tmpJ.isEmpty()) {
                return false;
            }
            arrayNode.removeAll();
            for(int i = 0; i < tmpJ.size(); i++) {
                arrayNode.insert(i, tmpJ.get(i));
            }
            return true;
        } else {
            return false;
        }
    }
}
