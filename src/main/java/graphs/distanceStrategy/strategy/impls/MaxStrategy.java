package graphs.coupling.distanceStrategy.strategy.impls;

import graphs.coupling.distanceStrategy.strategy.DistanceStrategy;

public class MaxStrategy implements DistanceStrategy {

    @Override
    public double appropriateDistance(double distance1, double distance2) {
        return Math.max(distance1, distance2);
    }
}
