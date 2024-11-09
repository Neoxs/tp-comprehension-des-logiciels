package clustering.models;

public class ClusterEven implements Comparable<ClusterEven> {

    private Cluster leftCluster;
    private Cluster rightCluster;
    private Double linkageDistance;

    public ClusterEven(){ }

    public Cluster getOtherCluster(Cluster c) {
    return leftCluster == c ? rightCluster : leftCluster;
  }

    public Cluster getLeftCluster() {
        return leftCluster;
    }

    public void setLeftCluster(Cluster lCluster) {
        this.leftCluster = lCluster;
    }

    public Cluster getRightCluster() {
        return rightCluster;
    }

    public void setRightCluster(Cluster rCluster) {
        this.rightCluster = rCluster;
    }

    public Double getLinkageDistance() {
        return linkageDistance;
    }

    public void setLinkageDistance(Double distance) {
        this.linkageDistance = distance;
    }


    @Override
    public int compareTo(ClusterEven o) {
        if (o == null || o.getLinkageDistance() == null) {
            return  -1;
        } else if (getLinkageDistance() == null) {
            return  1;
        } else {
            return getLinkageDistance().compareTo(o.getLinkageDistance());
        }
    }

    public Cluster agglomerate(int clusterId) {
        return agglomerate("cluster*" + clusterId);
    }


    public Cluster agglomerate(String name) {

        Cluster cluster = createCluster(name);

        setRoot(cluster);

        double weight = calculateWeightOfRoot();

        cluster.getDistance().setWeight(weight);

        return cluster;
    }


    private Cluster createCluster(String name) {

        Cluster cluster = new Cluster(name);

        cluster.setProximity(new Proximity(getLinkageDistance()));
        cluster.appendLeafNames(leftCluster.getLeafNames());
        cluster.appendLeafNames(rightCluster.getLeafNames());
        cluster.addChild(leftCluster);
        cluster.addChild(rightCluster);

        return cluster;
    }

    private void setRoot(Cluster cluster) {

        leftCluster.setRoot(cluster);

        rightCluster.setRoot(cluster);
    }

    private double calculateWeightOfRoot() {

        Double leftWeight = leftCluster.getWeightValue();

        Double rightWeight = rightCluster.getWeightValue();

        return leftWeight + rightWeight;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if (leftCluster != null) {
            sb.append(leftCluster.getName());
        }
        if (rightCluster != null) {
            if (sb.length() > 0) {
                sb.append(" + ");
            }
            sb.append(rightCluster.getName());
        }
        sb.append(" : ").append(linkageDistance);
        return sb.toString();
    }

}
