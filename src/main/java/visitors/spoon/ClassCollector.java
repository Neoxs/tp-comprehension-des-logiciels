package visitors.spoon;


import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class ClassCollector extends FamixCollector {

    public ClassCollector(CtModel ctModel) {
        super(ctModel);
    }

    public List<CtClass> getClasses() {
       return ctModel.getElements(new TypeFilter<CtClass>(CtClass.class));
    }

    @Override
    public void configCollector() {

    }
}
