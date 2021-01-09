package branchandbound;

import java.util.Arrays;
import java.util.Scanner;

public class BranchAndBound {
    private int[][]         wt;                             // Matrix of edge
    // weights
    private String[]        city;                           // Vector of city
    // names
    private int             n;                              // Dimension for wt
    // and city
//    static ArrayList<Main.Tour> soln    = new ArrayList<Main.Tour>();
    private int             bestTour;                       // Initialized in
    // init()
    private int             blocked;                        // Ditto
    private boolean         DEBUG   = true;                 // Show
    // accept/reject
    // decisions
    private boolean         VERBOSE = true;

    public void init(Scanner inp)
    {
        int sub1, sub2;
        String line;
        n = inp.nextInt(); //Baca Total Node
        System.out.println("Total Node: "+n);
        wt = new int[n][n];
        System.out.println("Size Matrix: "+n+"*"+n);
        city = new String[n];
        // Initially, there are NO edges; hence -1.
        for (sub1 = 0; sub1 < n; sub1++)
            Arrays.fill(wt[sub1], -1);
        inp.nextLine(); // Discard rest of first line
        for (sub1 = 0; sub1 < n; sub1++)
            city[sub1] = inp.nextLine();
        Arrays.sort(city); // Just to be sure (binarySearch)
        inp.nextLine(); // Discard blank spacing line;
        blocked = 0; // Accumulate ALL weights for upper bound
        while (inp.hasNext())
        {
            int head, tail;
            int dist;
            String src, dst;
            line = inp.nextLine(); // E.g.: "George" "Pasco" 91
            // Chop out the double-quoted substrings.
            head = line.indexOf('"') + 1;
            tail = line.indexOf('"', head);
            src = line.substring(head, tail);
//            System.out.println(head+" - "+tail+" - "+src);
            head = line.indexOf('"', tail + 1) + 1;
            tail = line.indexOf('"', head);
            dst = line.substring(head, tail);
//            System.out.println(head+" - "+tail+" - "+dst);
            dist = Integer.parseInt(line.substring(tail + 1).trim());
//            System.out.println(dist);
            sub1 = Arrays.binarySearch(city, src);
            sub2 = Arrays.binarySearch(city, dst);
            wt[sub1][sub2] = wt[sub2][sub1] = dist;
            blocked += dist;
        }
        blocked += blocked; // Double the total
        bestTour = blocked; // And initialize bestTour
//        System.out.println("blocket:"+blocked);
//        printMatrix(wt);
    }




    public static void printMatrix(int[][] a){
        int row = a.length;
        int col = a[0].length;
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                System.out.print(a[i][j]+"  ");
            }
            System.out.println();
        }
    }

    public static int[] stringToInt(String arr){
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            };
        }
        return results;
    }

    public int[][] getWt() {
        return wt;
    }

    public void setWt(int[][] wt) {
        this.wt = wt;
    }

    public String[] getCity() {
        return city;
    }

    public void setCity(String[] city) {
        this.city = city;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getBestTour() {
        return bestTour;
    }

    public void setBestTour(int bestTour) {
        this.bestTour = bestTour;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    public boolean isVERBOSE() {
        return VERBOSE;
    }

    public void setVERBOSE(boolean VERBOSE) {
        this.VERBOSE = VERBOSE;
    }
}
