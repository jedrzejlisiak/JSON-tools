package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

public class JSONComponentImp implements JSONComponent{
    private String JSON;
    public JSONComponentImp(String JSON){
        this.JSON = JSON;
    }
    @Override
    public String decorate() {
        return JSON;
    }
}
