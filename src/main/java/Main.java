import clustering.linkage.impls.AverageLinkageStrategy;
import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Cluster;
import clustering.process.strategy.impls.DefaultClusteringAlgorithm;
import clustering.process.strategy.interfaces.ClusteringAlgorithm;
import graphs.*;
import parsers.Jdt;
import parsers.Spoon;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc= new Scanner(System.in);
        System.out.println("\nWelcome to the software comprehension application, you need to provide project path and jdk path ");
        System.out.println("\nPlease provide the project path you want to work on it : ");
        String projectPath = sc.next();
        String projectSourcePath = projectPath + "/src";
        System.out.println("Please provide the JDK path : ");
        String jrePath = sc.next();
        System.out.println("\nMenu : ");
        System.out.println("1 : Response for questions using JDT.");
        System.out.println("2 : Response for questions using Spoon.");
        System.out.println("0 : Exit.");

        System.out.print("What do you choose : ");
        int input = sc.nextInt();


        while(input < 0 || input > 2 ){
            System.out.print("Wrong input, please choose again : ");
            input = sc.nextInt();
        }
        if (input==0){
            System.exit(0);
        }
        if (input==1){
            System.out.println("***** Call Graph using JDT *****");
            Graph jdtGraph = new JDTCallGraph(new Jdt(projectSourcePath, jrePath)).createCallGraph();
            System.out.println(jdtGraph.printInvocatins());

            CouplingGraphTools couplingGraphTools = new CouplingGraphTools(jdtGraph);
            couplingGraphTools.calculateMetrics();
            CouplingGraph couplingGraph = couplingGraphTools.getCouplingGraph();
            System.out.println(couplingGraph.toString());

        }
        else if (input==2){
            System.out.println("----- Call Graph using Spoon -----");
            Graph spoonGraph = new SpoonCallGraph(new Spoon(projectSourcePath, jrePath)).createCallGraph();
            System.out.println(spoonGraph.printInvocatins());

            CouplingGraphTools couplingGraphTools = new CouplingGraphTools(spoonGraph);
            couplingGraphTools.calculateMetrics();
            CouplingGraph couplingGraph = couplingGraphTools.getCouplingGraph();
            System.out.println(couplingGraph.toString());
        }

        String[] names =
                new String[] { "App", "A", "B", "Customer" };

        double[][] distancesMatrix = new double[][] {
                { 0, 1, 5, 2},
                { 1, 0, 4, 3 },
                { 5, 4, 0, 1 }
        };

        LinkageStrategy strategy =
                new AverageLinkageStrategy();

        Frame f1 = new Dendrogram(createSampleCluster(strategy, names, distancesMatrix) );
        f1.setSize(500, 500);
        f1.setLocation(400, 200);
    }

    private static Cluster createSampleCluster(LinkageStrategy strategy, String[] names, double[][] distancesMatrix ) {


        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.executeClustering(distancesMatrix, names, strategy);
        cluster.toConsole(0);
        System.err.println(selection_cluster(cluster));
        return cluster;
    }


    private static java.util.List<Cluster> selection_cluster(Cluster dendgr) {

        List<Cluster> R = new ArrayList<>();

        Stack<Cluster> parcoursCluster = new Stack<>();

        parcoursCluster.push(dendgr);

        while (!parcoursCluster.isEmpty()) {

            Cluster parent = parcoursCluster.pop();

            Cluster cl1 = parent.getChildren().get(0);
            Cluster cl2 = parent.getChildren().get(1);

            if (cl1 == null || cl2 == null) {
                R.add(parent);
                continue;
            }

            if ( S(parent) > avg( S(cl1) , S(cl2) ) ) {
                R.add(parent);
            } else {
                parcoursCluster.push(cl1);
                parcoursCluster.push(cl2);
            }
        }
        return R;

    }

    private static Double S(Cluster parent) {
        return parent.getDistanceValue();
    }

    private static Double avg(double value1, double value2) {
        return ( value1 + value1 ) / 2 ;
    }

}
