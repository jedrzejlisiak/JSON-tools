package pl.put.poznan.tools.logic;


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
