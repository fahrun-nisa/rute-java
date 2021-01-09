package branchandbound;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    static int[][]         wt;                             // Matrix of edge
    // weights
    static String[]        city;                           // Vector of city
    // names
    static int             n;                              // Dimension for wt
    // and city
//    static ArrayList<Main.Tour> soln    = new ArrayList<Main.Tour>();
    static int             bestTour;                       // Initialized in
    // init()
    static int             blocked;                        // Ditto
    static boolean         DEBUG   = true;                 // Show
    // accept/reject
    // decisions
    static boolean         VERBOSE = true;                 // Show all tours

    private static void init(Scanner inp)
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
        System.out.println("blocket:"+blocked);
        printMatrix(wt);
    }

    private static void generatePath(String startNode, String finishNode){
        int start, finish;
        start = Arrays.binarySearch(city, startNode);
        finish = Arrays.binarySearch(city, finishNode);

        for(int i=0; i<n; i++){
//            if(wt[start])
        }

        System.out.println(start);
        System.out.println(finish);
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
    public static void main(String[] args) throws FileNotFoundException {
        String start = "A";
        String finish = "M";
        String filename = args.length == 0 ? "src/data/RoadSet2.txt" : args[0];
        Scanner inp = new Scanner(new java.io.File(filename));
        System.out.println("Data read from file " + filename);
        init(inp);

        Graph graph = new Graph(n);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i != j){
                    if(wt[i][j] > 0){
                        graph.addEdge(i, j);
                    }
                }

            }
        }
        graph.printAllPaths(Arrays.binarySearch(city, start),Arrays.binarySearch(city, finish));
        List<String> allPath = graph.allPath;

        int bestRoute = 0;
        int bestDistance = 0;
        for (int i=0; i<allPath.size(); i++){
            System.out.println("Jalur ke-"+i);
            int totalDistance = 0;
            int[] pathInt = stringToInt(allPath.get(i));
            for (int j=0; j<(pathInt.length-1); j++){
                totalDistance += wt[pathInt[j]][pathInt[j+1]];
                System.out.println(city[pathInt[j]] + " -> " + city[pathInt[j+1]] + " = " + (wt[pathInt[j]][pathInt[j+1]]));
//                totalDistance += wt[j][j+1];

            }
            if (i==0) {
                bestDistance = totalDistance;
                bestRoute = i;
            }

            if (totalDistance<bestDistance) {
                bestDistance = totalDistance;
                bestRoute = i;
            }
            System.out.println("Total Distance "+totalDistance);
            System.out.println();
        }

        int[] pathInt = stringToInt(allPath.get(bestRoute));

        System.out.print("Best Route: ");
        for (int i =0; i<pathInt.length; i++){
            System.out.print(city[pathInt[i]]);
            if((i+1) != pathInt.length)
                System.out.print("-");
        }
        System.out.println();
        System.out.println("Distance: "+bestDistance);



//        generatePath(start, finish);
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

}
