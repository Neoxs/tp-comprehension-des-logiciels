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
        Scanner sc = new Scanner(System.in);

        String projectPath = getInput(sc, "Please provide the project path you want to work on: ");
        String jrePath = getInput(sc, "Please provide the JDK path: ");
        String projectSourcePath = projectPath + "/src";

        int input = getMenuSelection(sc);

        if (input == 0) {
            System.exit(0);
        }

        Graph callGraph = createCallGraph(input, projectSourcePath, jrePath);

        if (callGraph != null) {
            System.out.println(callGraph.printInvocatins());

            CouplingGraphTools couplingGraphTools = new CouplingGraphTools(callGraph);
            couplingGraphTools.calculateMetrics();
            CouplingGraph couplingGraph = couplingGraphTools.getCouplingGraph();
            System.out.println(couplingGraph.toString());
        }

        runClusteringExample();
    }

    // Method to handle user input for project path and JDK path
    private static String getInput(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.next();
    }

    // Method to handle menu input and validation
    private static int getMenuSelection(Scanner sc) {
        System.out.println("\nMenu:");
        System.out.println("1 : Response for questions using JDT.");
        System.out.println("2 : Response for questions using Spoon.");
        System.out.println("0 : Exit.");

        System.out.print("What do you choose: ");
        int input = sc.nextInt();

        while (input < 0 || input > 2) {
            System.out.print("Invalid choice. Please choose again: ");
            input = sc.nextInt();
        }
        return input;
    }

    // Method to handle Call Graph creation based on user's choice
    private static Graph createCallGraph(int input, String projectSourcePath, String jrePath) throws IOException {
        Graph graph = null;

        if (input == 1) {
            System.out.println("***** Call Graph using JDT *****");
            graph = new JDTCallGraph(new Jdt(projectSourcePath, jrePath)).createCallGraph();
        } else if (input == 2) {
            System.out.println("----- Call Graph using Spoon -----");
            graph = new SpoonCallGraph(new Spoon(projectSourcePath, jrePath)).createCallGraph();
        }
        return graph;
    }

    // Method to run the clustering example
    private static void runClusteringExample() {
        String[] names = { "App", "A", "B", "Customer" };
        double[][] distancesMatrix = {
                { 0, 1, 5, 2 },
                { 1, 0, 4, 3 },
                { 5, 4, 0, 1 }
        };

        LinkageStrategy strategy = new AverageLinkageStrategy();
        Cluster cluster = createSampleCluster(strategy, names, distancesMatrix);

        // Create and display dendrogram
        Frame dendrogramFrame = new Dendrogram(cluster);
        dendrogramFrame.setSize(500, 500);
        dendrogramFrame.setLocation(400, 200);
    }

    // Method to create sample clustering and output to console
    private static Cluster createSampleCluster(LinkageStrategy strategy, String[] names, double[][] distancesMatrix) {
        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.executeClustering(distancesMatrix, names, strategy);
        cluster.toConsole(0);

        // Output the selected clusters
        List<Cluster> selectedClusters = selectClusters(cluster);
        System.err.println(selectedClusters);
        return cluster;
    }

    // Method to select clusters based on custom criteria
    private static List<Cluster> selectClusters(Cluster rootCluster) {
        List<Cluster> selectedClusters = new ArrayList<>();
        Stack<Cluster> clusterStack = new Stack<>();
        clusterStack.push(rootCluster);

        while (!clusterStack.isEmpty()) {
            Cluster parent = clusterStack.pop();

            List<Cluster> children = parent.getChildren();
            if (children == null || children.size() < 2) {
                selectedClusters.add(parent);
                continue;
            }

            Cluster cl1 = children.get(0);
            Cluster cl2 = children.get(1);

            if (S(parent) > avg(S(cl1), S(cl2))) {
                selectedClusters.add(parent);
            } else {
                clusterStack.push(cl1);
                clusterStack.push(cl2);
            }
        }
        return selectedClusters;
    }

    // Helper method to get the distance value of a cluster
    private static Double S(Cluster cluster) {
        return cluster.getDistanceValue();
    }

    // Helper method to calculate the average of two distance values
    private static Double avg(double value1, double value2) {
        return (value1 + value2) / 2;
    }
}
