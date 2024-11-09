package graphs;

import graphs.distanceStrategy.NavigatorDistance;
import graphs.coupling.distanceStrategy.strategy.impls.AddStrategy;

import java.util.*;
import java.util.function.Consumer;

public class CouplingGraph {

    private Map<String, Map<String, Double>> couplingClasses  = new HashMap<>();

    private static NavigatorDistance navigatorDistance = new NavigatorDistance();

    private Set<String> names = new HashSet<>();

    private double[][] distances ;

    private Set<String> classes = new HashSet<>();

    public void setClasses(Set<String> classes) {
        this.classes = classes;
    }

    public Set<String> getClasses() {
        return classes;
    }

    public void setCouplingClasses(Map<String, Map<String, Double>> map) { this.couplingClasses = map; }

    public Map<String, Map<String, Double>> getCouplingGraph() { return this.couplingClasses; }

    public void addNodeToCouplingGraph(String source, Map<String, Double> map) {
        this.couplingClasses.put(source, map);
    }


    public String printCouplingGraph() {
        StringBuilder builder = new StringBuilder();
        builder.append("Coupling Graph");
        builder.append("\n");

        for (String source: couplingClasses.keySet() ) {
            builder.append(source + ":\n");

            for (String destination: couplingClasses.get(source).keySet())
                builder.append("\t---> " + destination +
                        " (" + couplingClasses.get(source).get(destination) + ")\n");
            builder.append("\n");
        }

        return builder.toString();
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }


    private Set<String> generateCouplingNames() {

        Set<String> sources = couplingClasses.keySet();

        Set<String> list = new HashSet<>();

        for (String src : sources) {
            list.addAll( couplingClasses.get(src).keySet() );
        }

        list.addAll(sources);
        setNames(list);
        return names;
    }

    public double[][] generateProximityMatrix(Set<String> classes) {

        List<String> stringsList = new ArrayList<>(classes);

        int initialDimension = classes.size();

        distances = new double[initialDimension][initialDimension];

        initProximityMatrix(classes);

        int i,j = 0;

        for (String src : couplingClasses.keySet()) {

            i = stringsList.indexOf(src);

            for(String destination : couplingClasses.get(src).keySet()) {

                j = stringsList.indexOf(destination);

                if ( j!= -1 ) {

                    distances[i][j] = calculateDistance(src, destination);

                    distances[j][i] = distances[i][j];
                }
            }
        }
        return distances;
    }


    private void initProximityMatrix(Set<String> classes) {
        int initialDimension = classes.size();
        for (int i = 0 ; i < initialDimension; i++) {
            for (int j = 0; j < initialDimension; j++ ) {
                distances[i][j] = 0 ;
            }
        }
    }


    public void printProximityMatrix() {

        Consumer<double[]> pipeDelimiter = (row) -> {
            Arrays.stream(row).forEach((el) -> System.out.print("| " + el + " "));
            System.out.println("|");
        };

        Arrays.stream(distances).forEach((row) -> pipeDelimiter.accept(row));
    }

    public double calculateDistance(String source, String destination) {

        double expectedDistance = 0.0 ;
        try {
            double distance1 = getFirstValueOfCoupling(source, destination);
            double distance2 = getSecondValueOfCoupling(destination, source);

            expectedDistance = 1 - calculateDistanceVariant(distance1, distance2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return expectedDistance;
    }

    protected double getFirstValueOfCoupling(String source, String destination) {
        return couplingClasses.get(source).get(destination);
    }

    protected double getSecondValueOfCoupling(String destination, String source) {
        double distanceInit = 0.0;
        if ( couplingClasses.containsKey(destination) && couplingClasses.get(destination).containsKey(source) ) {
            distanceInit = couplingClasses.get(destination).get(source);
        }
        return distanceInit;
    }

    protected double calculateDistanceVariant(double distance1, double distance2) {

        navigatorDistance.setDistanceStrategy(new AddStrategy());

        return navigatorDistance.calculateDistance(distance1, distance2);
    }
}
