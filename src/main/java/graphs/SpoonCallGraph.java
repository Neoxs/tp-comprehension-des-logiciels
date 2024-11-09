package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

public class SpoonCallGraph {
    private final Spoon parser;
    private CtModel ctModel;
    private final Set<String> classes = new HashSet<>();
    private final Graph graph = new Graph();
    private final GraphTools graphTools = new GraphTools();

    public SpoonCallGraph(Spoon parser) {
        this.parser = parser;
        setCtModel(parser);
        collectClassesAndInterfaces(parser);
    }

    private void setCtModel(Spoon parser) {
        this.ctModel = parser.createFAMIXModel();
    }

    private void collectClassesAndInterfaces(Spoon parser) {
        ClassCollector classCollector = new ClassCollector(ctModel);
        InterfaceCollector interfaceCollector = new InterfaceCollector(ctModel);
        addClassesToSet(classCollector);
    }

    private void addClassesToSet(ClassCollector classCollector) {
        Set<String> tempClasses = classCollector.getClasses().stream()
                .map(CtTypeInformation::getQualifiedName)
                .map(this::getShortClassName)
                .collect(Collectors.toSet());
        classes.addAll(tempClasses);
        System.out.println("\nClasses: " + classes);
    }

    private String getShortClassName(String fullClassName) {
        String[] parts = fullClassName.split("\\.");
        return parts[parts.length - 1];
    }

    public Graph createCallGraph() {
        ClassCollector classCollector = new ClassCollector(ctModel);
        MethodCollector methodCollector = MethodCollector.getInstance();
        MethodInvocationsCollector methodInvocationsCollector = MethodInvocationsCollector.getInstance();

        for (CtClass ctClass : classCollector.getClasses()) {
            Map<String, Map<String, String>> methodInvocations = new HashMap<>();
            for (CtMethod ctMethod : methodCollector.getMethodsOfClass(ctClass)) {
                Map<String, String> invocatedMethods = getInvocatedMethods(ctMethod, ctClass, methodInvocationsCollector);
                if (!invocatedMethods.isEmpty()) {
                    methodInvocations.put(ctMethod.getSignature(), invocatedMethods);
                }
            }
            if (!methodInvocations.isEmpty()) {
                graph.getClassesInvocations().put(ctClass.getQualifiedName(), methodInvocations);
            }
        }
        return graph;
    }

    private Map<String, String> getInvocatedMethods(CtMethod ctMethod, CtClass ctClass, MethodInvocationsCollector methodInvocationsCollector) {
        Map<String, String> invocatedMethods = new HashMap<>();
        String shortClassName = getShortClassName(ctClass.getQualifiedName());
        for (CtInvocation ctInvocation : methodInvocationsCollector.getMethodsInvocation(ctMethod)) {
            String invocatedClassName = graphTools.getClassOfInvocationedMethod(ctInvocation);
            if (!shortClassName.equals(invocatedClassName) && classes.contains(invocatedClassName)) {
                invocatedMethods.put(graphTools.getMethodInvocationName(ctInvocation), invocatedClassName);
            }
        }
        return invocatedMethods;
    }
}