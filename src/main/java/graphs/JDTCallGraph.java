package graphs;

import parsers.Jdt;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import visitors.jdt.ClassVisitor;
import visitors.jdt.MethodInvocationVisitor;
import visitors.jdt.MethodProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JDTCallGraph {

    private Jdt parser;
    private Set<String> classes = new HashSet<>();
    ClassVisitor classVisitor = new ClassVisitor();
    Graph graph = new Graph();
    GraphTools graphTools = new GraphTools();

    public JDTCallGraph(Jdt parser){
        this.parser = parser;
    }

    private void setClasses() throws IOException {
        for (CompilationUnit cu: parser.parseProject()) {
            cu.accept(classVisitor);
            classes.addAll(
                    classVisitor
                            .getClasses()
                            .stream()
                            .map(e -> e.getName().getIdentifier())
                            .collect(Collectors.toList())
            );
        }

        System.out.println("\nClasses : " + classes);
    }

    public Set<String> getClasses() {
        return classes;
    }

    public Graph createCallGraph() {
        try {
            setClasses();
            for (CompilationUnit cu: parser.parseProject()) {
                String className = "";
                Map<String, Map<String, String>> methodsInvocations = new HashMap<>();
                Map<String, String> targetClassesInvocations;

                MethodProcessor methodProcessor = new MethodProcessor();
                methodProcessor.processMethods(cu);

                for (MethodDeclaration method : methodProcessor.getMethods()) {

                    Map<String, String> tmpMap = new HashMap<>();

                    className = graphTools.getClassName(method);

                    MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();

                    method.accept(methodInvocationVisitor);

                    if (methodInvocationVisitor.getMethods().size() > 0) {

                        for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                            String classOfInvocationedMethod = graphTools.getClassOfInvocationedMethod(methodInvocation);

                            if (!classOfInvocationedMethod.equals(className) && (classes.contains(classOfInvocationedMethod)))
                                tmpMap.put( graphTools.getMethodInvocationName(methodInvocation), classOfInvocationedMethod);

                        }

                        targetClassesInvocations = tmpMap;
                        methodsInvocations.put( graphTools.getMethodNameAndParams(method), targetClassesInvocations);
                        graph.getClassesInvocations().put( graphTools.getClassName(method), methodsInvocations);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

}
