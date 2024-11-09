package clustering.process.strategy.impls;

import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Cluster;
import clustering.models.ProximityDic;
import clustering.process.builder.HierarchyBuilder;
import clustering.process.factories.ClustersFactory;
import clustering.process.factories.ProximityDicFactory;
import clustering.process.strategy.interfaces.ClusteringAlgorithm;
import clustering.process.validator.ArgumentsValidator;
import clustering.process.validator.IValidator;
import java.util.List;

public class DefaultClusteringAlgorithm implements ClusteringAlgorithm
{


    private IValidator iValidator;


    @Override
    public Cluster executeClustering(double[][] distances,
                                     String[] clusterNames, LinkageStrategy linkageStrategy)
    {
        iValidator = new ArgumentsValidator();
        iValidator.checkInputs(distances, clusterNames, linkageStrategy);


        List<Cluster> clusters = ClustersFactory.getInstance().createClusters(clusterNames) ;
        ProximityDic linkages = ProximityDicFactory.getInstance().createProximityDic(distances, clusters);

        HierarchyBuilder builder = new HierarchyBuilder(clusters, linkages);
        while (!builder.isTreeComplete())
        {
            builder.agglomerate(linkageStrategy);
        }

        return builder.getRootCluster();
    }

}
