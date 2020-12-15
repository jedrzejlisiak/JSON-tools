package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONDeminification extends JSONDecorator{

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
        catch(Throwable r){
            System.out.println("tosienaprawi");
            return "niedziala";
        }
    }
}
