package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class JsonTools {

    private String JSON1, JSON2;
    public JsonTools(String JSON1, String JSON2){
        this.JSON1 = JSON1;
        this.JSON2 = JSON2;
    }

    public String transform(String transform, String no, String fields){
        if (transform.equals("deminify")){
            //System.out.println("tutaj");
            if(no.equals("1")){
                JSONComponent c = new JSONDeminification(new JSONComponentImp(JSON1));
                return c.decorate();
            }
            else {
                JSONComponent c = new JSONDeminification(new JSONComponentImp(JSON2));
                return c.decorate();
            }
        }
        else if(transform.equals("minify")){
            if(no.equals("1")){
                JSONComponent c = new JSONMinification(new JSONComponentImp(JSON1));
                return c.decorate();
            }
            else{
                JSONComponent c = new JSONMinification(new JSONComponentImp(JSON2));
                return c.decorate();
            }
        }
        else if(transform.equals("filterNeg")) {
            if (no.equals("1")) {
                JSONComponent c = new JSONNegFilter(new JSONComponentImp(JSON1), fields);
                return c.decorate();
            } else {
                JSONComponent c = new JSONNegFilter(new JSONComponentImp(JSON2), fields);
                return c.decorate();
            }
        }
        else if(transform.equals("compare")){
            JSONComparator compa = new JSONComparator(JSON1, JSON2);
            return compa.checkFiles();
        }
        else if(transform.equals("filterPos")) {
            if (no.equals("1")) {
                JSONComponent c = new JSONPosFiltration(new JSONComponentImp(JSON1), fields);
                return c.decorate();
            } else {
                JSONComponent c = new JSONPosFiltration(new JSONComponentImp(JSON2), fields);
                return c.decorate();
            }
        }
        return JSON1.toString();
    }
}
