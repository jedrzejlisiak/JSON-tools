package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JSONDecorator implements JSONComponent{
    protected JSONComponent comp;

    public JSONDecorator(JSONComponent comp){
        this.comp = comp;
    }

    @Override
    public String decorate() {
        return comp.decorate();
    }
}
