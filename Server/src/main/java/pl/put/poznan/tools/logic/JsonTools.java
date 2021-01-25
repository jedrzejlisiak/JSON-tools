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
     * @param JSON1 is a JSON file.
     * @param JSON2 is a JSON file.
     */
    public JsonTools(String JSON1, String JSON2){
        this.JSON1 = JSON1;
        this.JSON2 = JSON2;
    }
    /**
     *  This method processes a file.
     *
     * @param transform transform is a string with transforms list.
     * @param no no is a number of file.
     * @param fields fields is a string with feilds to be filtered.
     * @return processed JSON.
     */
    public String transform(String transform, String no, String fields){

        JSONComponent c = new JSONComponentImp(JSON1);

        ArrayList<String> transformations = new ArrayList<String>(Arrays.asList(transform.split(",")));

        if(transformations.contains("toJson")) {
            transformations.remove("toJson");
            c = new JSONXmlToJson(c);
        }

        if(transformations.contains("filterNeg")) {
            transformations.remove("filterNeg");
            c = new JSONNegFilter(c, fields);
        }

        if(transformations.contains("filterPos")) {
            transformations.remove("filterPos");
            c = new JSONPosFiltration(c, fields);
        }

        if (transformations.contains("deminify")&&!transformations.contains("toXml")){
            transformations.remove("deminify");
            c = new JSONDeminification(c);
        }
        if(transformations.contains("minify")&&!transformations.contains("toXml")){
            c = new JSONMinification(c);
        }
        if(transformations.contains("toXml")){
            transformations.remove("toXml");
            if(transformations.contains("deminify")){
                transformations.remove("deminify");
                c = new JSONDeminificationXML(c);
            }
            else {
                transformations.remove("minify");
                c = new JSONMinificationXML(c);
            }
        }
        if(transformations.contains("compare")) {
            JSONComparator compa = new JSONComparator(JSON1, JSON2);
            return compa.checkFiles();
        }

        return c.decorate();
    }
}
