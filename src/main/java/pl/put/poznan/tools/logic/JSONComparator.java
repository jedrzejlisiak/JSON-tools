package pl.put.poznan.tools.logic;

public class JSONComparator {

    private String f1,f2;
    public JSONComparator(String ff1, String ff2){
        f1 = ff1;
        f2 = ff2;
    }

    public String checkFiles(){

        if (f1.equals("") && f2.equals("")){
            return("oba jsony puste");
        }
        else if (f1.equals("") || f2.equals("")){
            return("jeden z jsonów jest pusty");
        }
        else if (f1.equals(f2)){
            return("jsony są identyczne");
        }
        else{
            compareStr(f1,f2);
            return("porównywanie brrrrr");

        }

    }

    public void compareStr(String f1, String f2){

    }

}
