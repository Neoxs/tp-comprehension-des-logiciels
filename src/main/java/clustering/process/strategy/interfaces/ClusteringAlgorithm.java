package clustering.process.strategy.interfaces;

import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Cluster;


public interface ClusteringAlgorithm
{
    public Cluster executeClustering(double[][] distances, String[] clusterNames,
                                     LinkageStrategy linkageStrategy);
}
