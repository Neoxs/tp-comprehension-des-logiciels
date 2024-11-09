package clustering.process.builder;

import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Cluster;
import clustering.models.ClusterEven;
import clustering.models.Proximity;
import clustering.models.ProximityDic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HierarchyBuilder {

    private ProximityDic distances;

    private List<Cluster> clusters;

    private int globalClusterIndex = 0;

    public ProximityDic getDistances() {
        return distances;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public HierarchyBuilder(List<Cluster> clusters, ProximityDic distances) {
        this.clusters = clusters;
        this.distances = distances;
    }


    public void agglomerate(LinkageStrategy linkageStrategy) {

        ClusterEven minDistLink = distances.removeFirst();

        if (minDistLink != null) {

            clusters.remove(minDistLink.getRightCluster());

            clusters.remove(minDistLink.getLeftCluster());

            Cluster oldClusterL = minDistLink.getLeftCluster();

            Cluster oldClusterR = minDistLink.getRightCluster();

            Cluster newCluster = minDistLink.agglomerate(++globalClusterIndex);

            for (Cluster iClust : clusters) {

                ClusterEven link1 = findByClusters(iClust, oldClusterL);

                ClusterEven link2 = findByClusters(iClust, oldClusterR);

                ClusterEven newLinkage = new ClusterEven();

                newLinkage.setLeftCluster(iClust);

                newLinkage.setRightCluster(newCluster);

                Collection<Proximity> proximityValues = new ArrayList<Proximity>();

                if (link1 != null) {
                    Double distVal = link1.getLinkageDistance();
                    Double weightVal = link1.getOtherCluster(iClust).getWeightValue();
                    proximityValues.add(new Proximity(distVal, weightVal));
                    distances.remove(link1);
                }

                if (link2 != null) {
                    Double distVal = link2.getLinkageDistance();
                    Double weightVal = link2.getOtherCluster(iClust).getWeightValue();
                    proximityValues.add(new Proximity(distVal, weightVal));
                    distances.remove(link2);
                }

                Proximity newProximity = linkageStrategy.calculateProximity(proximityValues);

                newLinkage.setLinkageDistance(newProximity.getProximityValue());

                distances.add(newLinkage);
            }
            clusters.add(newCluster);
        }
    }

    private ClusterEven findByClusters(Cluster c1, Cluster c2) {
        return distances.findByCodePair(c1, c2);
    }

    public boolean isTreeComplete() {
        return clusters.size() == 1;
    }

    public Cluster getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return clusters.get(0);
    }

}
