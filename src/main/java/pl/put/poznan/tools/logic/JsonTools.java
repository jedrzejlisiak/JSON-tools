package pl.put.poznan.tools.logic;


import java.util.ArrayList;
import java.util.Arrays;

/**
 *  Class purpose is to control JSONtools.
 *
 * @author Lubomir Basinski
 */
public class JsonTools {

    private String JSON1, JSON2;
    /**
     *  This is constructor that saves file.
     *
     * @param JSON1 s is a JSON file.
     * @param JSON2 s is a JSON file.
     */
    public JsonTools(String JSON1, String JSON2){
        this.JSON1 = JSON1;
        this.JSON2 = JSON2;
    }
    /**
     *  This is Deminification decoration function.
     *
     * @param transform transform is a string with transforms list.
     * @param no no is a number of file.
     * @param fields fields is a string with feilds to be filtrated.
     * @return processed JSON.
     */
    public String transform(String transform, String no, String fields){

        JSONComponent c;
        if(no.equals("1")) {
            c = new JSONComponentImp(JSON1);
        }
        else {
            c = new JSONComponentImp(JSON2);
        }

        ArrayList<String> transformations = new ArrayList<String>(Arrays.asList(transform.split(",")));

        if(transformations.contains("filterNeg")) {
            transformations.remove("filterNeg");
            c = new JSONNegFilter(c, fields);
        }

        if(transformations.contains("filterPos")) {
            transformations.remove("filterPos");
            c = new JSONPosFiltration(c, fields);
        }

        if (transformations.contains("deminify")){
            transformations.remove("deminify");
            c = new JSONDeminification(c);
        }
        if(transformations.contains("minify")){
            c = new JSONMinification(c);
        }
        else if(transform.equals("compare")) {
            JSONComparator compa = new JSONComparator(JSON1, JSON2);
            return compa.checkFiles();
        }

        return c.decorate();
    }
}
