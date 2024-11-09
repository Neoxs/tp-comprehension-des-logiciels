package graphs;

import parsers.Spoon;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtTypeInformation;
import visitors.spoon.ClassCollector;
import visitors.spoon.InterfaceCollector;
import visitors.spoon.MethodCollector;
import visitors.spoon.MethodInvocationsCollector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class SpoonCallGraph  {

    private Spoon parser;
    private CtModel ctModel;
    private Set<String> classes = new HashSet<>();
    Graph graph = new Graph();
    GraphTools graphUtilities = new GraphTools();

    public SpoonCallGraph(Spoon parser){
        setParser(parser);
        setCtModel(parser);
    }

    public void setParser(Spoon parser) {
        this.parser = parser;
    }

    public void setCtModel(Spoon parser) { this.ctModel = parser.createFAMIXModel(); }

    public void setClasses(ClassCollector classCollector,
                           InterfaceCollector interfaceCollector) {

        Set<String> tempClasses = classCollector.getClasses().stream().map(CtTypeInformation::getQualifiedName).collect(Collectors.toSet());

        for(String klass : tempClasses){
            String[] arr = klass.split("\\.");
            classes.add(arr[arr.length-1]);
        }

        System.out.println("\nClasses : " + classes);
    }

    public Set<String> getClasses() {
        return classes;
    }


    public Graph createCallGraph() {

        ClassCollector classCollector = new ClassCollector(ctModel);
        InterfaceCollector interfaceCollector  = new InterfaceCollector(ctModel);
        setClasses(classCollector, interfaceCollector);

        MethodCollector methodCollector = MethodCollector.getInstance();
        MethodInvocationsCollector methodInvocationsCollector = MethodInvocationsCollector.getInstance();

        for(CtClass ctClass: classCollector.getClasses()) {

            Map<String, Map<String, String>> methodsInvocations = new HashMap<>();
            Map<String, String> targetClassesInvocations;
            //System.out.println("current class : " + ctClass.getQualifiedName());
            for (CtMethod ctMethod : methodCollector.getMethodsOfClass(ctClass)) {
                //System.out.println("current method : " + ctMethod.getSimpleName());

                Map<String, String> tmpMap = new HashMap<>();

                if(methodInvocationsCollector.getMethodsInvocation(ctMethod).size() > 0) {

                    for (CtInvocation ctInvocation: methodInvocationsCollector.getMethodsInvocation(ctMethod)) {

                        String[] arr = ctClass.getQualifiedName().split("\\.");
                        String tempName = arr[arr.length-1];

                        String classOfInvocationedMethod = graphUtilities.getClassOfInvocationedMethod(ctInvocation);
                        if ( !classOfInvocationedMethod.equals(tempName) && (classes.contains(classOfInvocationedMethod)) ) {
                            tmpMap.put(  graphUtilities.getMethodInvocationName(ctInvocation), classOfInvocationedMethod);
                        }
                    }
                    targetClassesInvocations = tmpMap;
                    methodsInvocations.put( ctMethod.getSimpleName() + ctMethod.getParameters() , targetClassesInvocations);
                    graph.getClassesInvocations().put( ctClass.getQualifiedName(), methodsInvocations);

                }
            }
        }
        return graph;
    }
}
