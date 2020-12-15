package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

public class JSONComponentImp implements JSONComponent{
    private JsonNode JSON;
    public JSONComponentImp(JsonNode JSON){
        this.JSON = JSON;
    }
    @Override
    public String decorate() {
        return JSON.toString();
    }
}
