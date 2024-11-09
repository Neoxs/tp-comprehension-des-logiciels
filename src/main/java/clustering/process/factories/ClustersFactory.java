package clustering.process.factories;

import clustering.models.Cluster;

import java.util.ArrayList;
import java.util.List;

public class ClustersFactory {

    private static ClustersFactory instance;

    private ClustersFactory() {

    }

    public static ClustersFactory getInstance() {
        if (instance == null) {
            instance = new ClustersFactory();
        }
        return instance;
    }

    public List<Cluster> createClusters(String[] clusterNames)
    {
        List<Cluster> clusters = new ArrayList<Cluster>();
        for (String clusterName : clusterNames)
        {
            Cluster cluster = new Cluster(clusterName);
            cluster.addLeafName(clusterName);
            clusters.add(cluster);
        }
        return clusters;
    }

}
