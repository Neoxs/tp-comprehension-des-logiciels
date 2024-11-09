package visitors.spoon;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class InterfaceCollector extends FamixCollector {

    public InterfaceCollector(CtModel ctModel) {
        super(ctModel);
    }

    @Override
    public void configCollector() {
    }

    public List<CtInterface> getInterfaces() {
        return ctModel.getElements(new TypeFilter<CtInterface>(CtInterface.class));
    }
}
