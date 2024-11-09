package clustering.linkage.impls;

import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Proximity;

import java.util.Collection;

public class AverageLinkageStrategy implements LinkageStrategy {


	@Override
	public Proximity calculateProximity(Collection<Proximity> proximities) {

		double sum = 0;
		double result;

		for (Proximity dist : proximities) {
			sum += dist.getProximityValue();
		}
		if (proximities.size() > 0) {
			result = sum / proximities.size();
		} else {
			result = 0.0;
		}
		return  new Proximity(result);
	}
}
