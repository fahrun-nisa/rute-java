package branchandbound;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestBranchAndBound  {
    public static void main(String[] args) throws FileNotFoundException {
        BranchAndBound branchAndBound = new BranchAndBound();
        String start = "A";
        String finish = "M";
        String filename = "src/data/RoadSet2.txt";
        Scanner inp = new Scanner(new java.io.File(filename));
        System.out.println("Data read from file " + filename);
        branchAndBound.init(inp);

        int n = branchAndBound.getN();
        int[][] wt = branchAndBound.getWt();
        String[] city = branchAndBound.getCity();



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
            int[] pathInt = BranchAndBound.stringToInt(allPath.get(i));
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

        int[] pathInt = BranchAndBound.stringToInt(allPath.get(bestRoute));

//        System.out.print("Best Route: ");
        String bestRouteString = "";
        for (int i =0; i<pathInt.length; i++){
//            System.out.print(city[pathInt[i]]);
            bestRouteString += city[pathInt[i]];
            if((i+1) != pathInt.length)
                bestRouteString += " -> ";
        }
        bestRouteString += "\nDistance " + bestDistance;

        System.out.println(bestRouteString);



    }
}
