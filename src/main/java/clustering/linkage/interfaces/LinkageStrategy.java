package clustering.linkage.interfaces;

import clustering.models.Proximity;

import java.util.Collection;

public interface LinkageStrategy {

    public Proximity calculateProximity(Collection<Proximity> proximities);
}
