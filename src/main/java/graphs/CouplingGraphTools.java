package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouplingGraphTools {

    private Graph graph;
    private CouplingGraph couplingGraph = new CouplingGraph();

    public CouplingGraphTools(Graph graph) { this.graph = graph; }
    public CouplingGraph getCouplingGraph() {
        return couplingGraph;
    }

    public void calculateMetrics() {

        Map<String, Map<String, Map<String, String>>> classesInvocations = graph.getClassesInvocations();
        double nbrInvocation = (double) graph.getTotalInvocations();

        for (String currentClass: classesInvocations.keySet()) {
            //System.out.println(currentClass);
            Map<String, Double> map = new HashMap();
            List<String> list = new ArrayList<>();
            for (String currentClassCurrentMethod: classesInvocations.get(currentClass).keySet()) {
                //System.out.println(currentClassCurrentMethod);
                list.addAll(classesInvocations.get(currentClass).get(currentClassCurrentMethod).values());
                for (String classOfCurrentInvokedMethod: classesInvocations.get(currentClass).get(currentClassCurrentMethod).values()) {
                    double total = (double) (map.get(classOfCurrentInvokedMethod)!=null ? map.get(classOfCurrentInvokedMethod) + 1 : 1);
                    map.put(classOfCurrentInvokedMethod, total);

                }
            }
            couplingGraph.addNodeToCouplingGraph(currentClass, map);
            list.clear();
        }

        for (String currentClass : couplingGraph.getCouplingGraph().keySet()) {
            for (String currentClassCurrentMethod: couplingGraph.getCouplingGraph().get(currentClass).keySet()) {
                double ratio = couplingGraph.getCouplingGraph().get(currentClass).get(currentClassCurrentMethod);
                couplingGraph.getCouplingGraph().get(currentClass).put(currentClassCurrentMethod, ratio/nbrInvocation);
            }
        }

        System.out.println(couplingGraph.printCouplingGraph());
    }
}
