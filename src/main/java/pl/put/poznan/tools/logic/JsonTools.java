package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JsonTools {

    private JsonNode JSON1, JSON2;
    public JsonTools(JsonNode JSON1, JsonNode JSON2){
        this.JSON1 = JSON1;
        this.JSON2 = JSON2;
    }

    public String transform(String transform){
        if (transform.equals("deminify")){
            System.out.println("tutaj");
            JSONComponent c = new JSONDeminification(new JSONComponentImp(JSON1));
            return c.decorate();
        }
        return JSON1.toString();
    }
}
