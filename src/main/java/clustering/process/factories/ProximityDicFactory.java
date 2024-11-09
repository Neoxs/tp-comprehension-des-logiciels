package clustering.process.factories;

import clustering.models.Cluster;
import clustering.models.ClusterEven;
import clustering.models.ProximityDic;

import java.util.List;

public class ProximityDicFactory {

    private static ProximityDicFactory instance;

    private ProximityDic linkage ;

    private ProximityDicFactory() {
        linkage = new ProximityDic();
    }

    public static ProximityDicFactory getInstance() {
        if (instance == null) {
            instance = new ProximityDicFactory();
        }
        return instance;
    }


    public ProximityDic createProximityDic(double[][] distances,
                                           List<Cluster> clusters)
    {
        for (int col = 0; col < clusters.size(); col++)
        {
            for (int row = col + 1; row < clusters.size(); row++)
            {
                ClusterEven link = new ClusterEven();
                Cluster lCluster = clusters.get(col);
                Cluster rCluster = clusters.get(row);
                link.setLinkageDistance(distances[col][row]);
                link.setLeftCluster(lCluster);
                link.setRightCluster(rCluster);
                linkage.add(link);
            }
        }
        return linkage;
    }
}
