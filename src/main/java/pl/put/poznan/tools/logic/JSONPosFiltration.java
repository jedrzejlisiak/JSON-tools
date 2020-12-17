package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

public class JSONPosFiltration extends JSONDecorator {
    private final ArrayList<String> fields;

    public JSONPosFiltration(JSONComponent comp, String f) {
        super(comp);
        String[] tmpfields = f.split(",");
        fields = new ArrayList<>(Arrays.asList(tmpfields));
    }

    @Override
    public String decorate() {
        return DecoratePos(comp.decorate());
    }

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

    public boolean traverse(JsonNode root){
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
