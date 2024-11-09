package clustering.process.builder;

import clustering.models.Cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PartitionBuilder {

    private static PartitionBuilder instance;

    private PartitionBuilder() {}

    public static PartitionBuilder getInstance(){
        if (instance == null) {
            instance = new PartitionBuilder();
        }
        return instance;
    }

    public static List<Cluster> selection_cluster(Cluster dendgr) {

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
