package pl.put.poznan.tools.logic;

public class JSONComparator {

    private String f1,f2;
    public JSONComparator(String ff1, String ff2){
        f1 = ff1;
        f2 = ff2;
    }

    public String checkFiles(){

        if (f1.equals("") && f2.equals("")){
            return "Oba jsony są puste";
        }
        else if (f1.equals("") || f2.equals("")){
            return "Jeden z jsonów jest pusty";
        }
        else if (f1.equals(f2)){
            return "Jsony są identyczne";
        }
        else{
            return compareStr();


        }

    }

    public String comparation(String f1, String f2, int n) {

        if (f1.equals(f2)){
            return "";
        }
        else{
            return String.format("Unequal line nr \"%d\":    ",n)+ f1 + "    " + f2 + "\n";
        }

    }

    public String compareStr(){
        String s1 = new JSONDeminification(new JSONComponentImp(f1)).decorate();
        String s2 = new JSONDeminification(new JSONComponentImp(f2)).decorate();
        f1 = s1;
        f2 = s2;
        String lines1[] = f1.split("\\r?\\n");
        String lines2[] = f2.split("\\r?\\n");

        int l1 = lines1.length;
        int l2 = lines2.length;

        String output = "";

        if (l1 > l2) {
            for (int i = 0; i < l2; i++) {
                output +=  comparation(lines1[i] ,lines2[i], i);

            }
            for (int j = l2; j < l1; j++) {
                output +=  comparation(lines1[j], " ", j);
            }

        }
        else{
            for (int i = 0; i < l1; i++) {
                output +=  comparation(lines1[i] ,lines2[i], i);
            }
            for (int j = l1; j < l2; j++) {
                output +=  comparation(" ", lines2[j], j);
            }
        }

        return output;

    }


}
