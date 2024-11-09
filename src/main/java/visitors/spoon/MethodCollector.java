package visitors.spoon;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.Set;

public class MethodCollector{

    private static MethodCollector instance;

    private MethodCollector() { }

    public static MethodCollector getInstance() {
        if (instance == null) {
            instance = new MethodCollector();
        }
        return instance;
    }

    public Set<CtMethod> getMethodsOfClass(CtClass ctClass) {
        return ctClass.getMethods();
    }



}
