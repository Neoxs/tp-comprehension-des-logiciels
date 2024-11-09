package graphs;

import java.util.HashMap;
import java.util.Map;

public class CouplingGraphTools {
    private final Graph graph;
    private final CouplingGraph couplingGraph;

    public CouplingGraphTools(Graph graph) {
        this.graph = graph;
        this.couplingGraph = new CouplingGraph();
    }

    public CouplingGraph getCouplingGraph() {
        return couplingGraph;
    }

    public void calculateMetrics() {
        Map<String, Map<String, Map<String, String>>> classesInvocations = graph.getClassesInvocations();
        double totalInvocations = graph.getTotalInvocations();

        calculateInvocationCounts(classesInvocations);
        normalizeInvocationRatios(totalInvocations);

        System.out.println(couplingGraph.printCouplingGraph());
    }

    private void calculateInvocationCounts(Map<String, Map<String, Map<String, String>>> classesInvocations) {
        for (Map.Entry<String, Map<String, Map<String, String>>> classEntry : classesInvocations.entrySet()) {
            String currentClass = classEntry.getKey();
            Map<String, Map<String, String>> methods = classEntry.getValue();
            
            Map<String, Double> invocationCounts = calculateInvocationCountsForClass(methods);
            couplingGraph.addNodeToCouplingGraph(currentClass, invocationCounts);
        }
    }

    private Map<String, Double> calculateInvocationCountsForClass(Map<String, Map<String, String>> methods) {
        Map<String, Double> invocationCounts = new HashMap<>();
        
        for (Map<String, String> methodInvocations : methods.values()) {
            for (String invokedClass : methodInvocations.values()) {
                invocationCounts.merge(invokedClass, 1.0, Double::sum);
            }
        }
        
        return invocationCounts;
    }

    private void normalizeInvocationRatios(double totalInvocations) {
        Map<String, Map<String, Double>> couplingGraphData = couplingGraph.getCouplingGraph();
        
        for (Map<String, Double> classInvocations : couplingGraphData.values()) {
            for (Map.Entry<String, Double> invocation : classInvocations.entrySet()) {
                String invokedClass = invocation.getKey();
                double count = invocation.getValue();
                classInvocations.put(invokedClass, count / totalInvocations);
            }
        }
    }
}