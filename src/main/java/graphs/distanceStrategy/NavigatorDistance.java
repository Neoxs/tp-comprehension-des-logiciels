package graphs.distanceStrategy;

import graphs.coupling.distanceStrategy.strategy.DistanceStrategy;

public class NavigatorDistance {

    private DistanceStrategy distanceStrategy;


    public NavigatorDistance() {}


    public void setDistanceStrategy(DistanceStrategy distanceStrategy) {
        this.distanceStrategy = distanceStrategy;
    }

    public double calculateDistance(double distance1, double distance2) {
        return distanceStrategy.appropriateDistance(distance1, distance2);
    }
}
