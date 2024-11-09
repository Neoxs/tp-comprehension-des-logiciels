package clustering.process.validator;

import clustering.linkage.interfaces.LinkageStrategy;

public interface IValidator {

    public void checkInputs(double[][] distances, String[] clusterNames,
                         LinkageStrategy linkageStrategy);

}
