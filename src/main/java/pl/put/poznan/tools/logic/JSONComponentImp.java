package main.java.pl.put.poznan.tools.logic;


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
