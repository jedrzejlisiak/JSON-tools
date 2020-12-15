package pl.put.poznan.tools.logic;



public class JSONMinification extends JSONDecorator{

    public JSONMinification(JSONComponent comp) {
        super(comp);
    }
    @Override
    public String decorate() {
        return decorateMini(comp.decorate());
    }
    private String decorateMini(String s){
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
