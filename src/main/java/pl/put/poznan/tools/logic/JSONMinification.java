package pl.put.poznan.tools.logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMinification extends JSONDecorator{

    public JSONMinification(JSONComponent comp) {
        super(comp);
    }
    @Override
    public String decorate() {
        return decorateMini(comp.decorate());
    }
    private String decorateMini(String s){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try {
            node = mapper.readTree(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Failed do minify";
        }
        String nJson = node.toString();
        String output = "";
        boolean coma = false;
        for(int i = 0, n = s.length() ; i < n ; i++) {
            char c = s.charAt(i);
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
