package pl.put.poznan.tools.logic;

/**
 *  Class purpose is to find differences in two JSON files
 *
 * @author Jerzy Weber
 */


public class JSONComparator {

    private String f1,f2;

    /**
     *  This is a constructor
     *
     * @param ff1 f1 is a JSON file fed to the function as a string
     * @param ff2 f2 is a JSON file fed to the function as a string
     */

    public JSONComparator(String ff1, String ff2){
        f1 = ff1;
        f2 = ff2;
    }


    /**
     * This method compares two lines from different JSON files
     *
     * @param f1 f1 is a line from the first file fed to the function as a string
     * @param f2 f2 is a line from the second file fed to the function as a string
     * @param n n is the index of lines which is currently being compared
     *
     *
     * @return returns either an empty string in case the lines do not differ, or
     * or a string which includes the index of the line and both lines
     */

    private String comparison(String f1, String f2, int n) {
        int l1 = f1.length();
        if (l1 > 50){ l1 = 50;}


        String outStr = "";

        if (f1.equals(f2)){
            return "";
        }
        else{

            outStr += f1;
            for (int i = 0; i < (50 - l1); i++){
                outStr += " ";
            }
            outStr += f2;

            return String.format("Unequal line nr \"%d\":    ",n)+ outStr + "\n";
        }

    }

    /**
     *  This method finds differences in two JSON files
     *
     *  @return returns lines which differ in both files and their index
     */

    public String checkFiles(){
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
                output +=  comparison(lines1[i] ,lines2[i], i);

            }
            for (int j = l2; j < l1; j++) {
                output +=  comparison(lines1[j], " ", j);
            }

        }
        else{
            for (int i = 0; i < l1; i++) {
                output +=  comparison(lines1[i] ,lines2[i], i);
            }
            for (int j = l1; j < l2; j++) {
                output +=  comparison(" ", lines2[j], j);
            }
        }

        return output;

    }


}
