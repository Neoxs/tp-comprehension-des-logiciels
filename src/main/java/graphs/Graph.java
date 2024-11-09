package graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    // 1st String > class name
    // 2nd String > method name
    // 3rd String > invoked method
    // 4th String > class of the invoked method

    private Map<String, Map<String, Map<String, String>>> classesInvocations = new HashMap<>();

    public Map<String, Map<String, Map<String, String>>> getClassesInvocations() {
        return classesInvocations;
    }

    // Total method invocations
    public long getTotalInvocations() {
        return classesInvocations.keySet()
                .stream()
                .map(source -> classesInvocations.get(source))
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(Map::keySet)
                .mapToLong(Collection::size)
                .sum();
    }


    public String printInvocatins() {
        String consoleOutput = "";
        consoleOutput += "\nMethods Call : ";
        consoleOutput += "\nTotal Methods Invocation =  "+getTotalInvocations()+".";
        consoleOutput += "\n";


        for (String currentClass: classesInvocations.keySet()) {
            consoleOutput += currentClass + ":\n";
            for (String currentMethod: classesInvocations.get(currentClass).keySet()) {
                consoleOutput += "\t- Method : " + currentMethod + ":\n";
                for (String currentCalledMethod : classesInvocations.get(currentClass).get(currentMethod).keySet()) {
                    String classNameOfTheInvokedMethod = classesInvocations.get(currentClass).get(currentMethod).get(currentCalledMethod);
                    consoleOutput += "\t\t- Called Method : " + currentCalledMethod + " -- Class Name : " + classNameOfTheInvokedMethod;
                    consoleOutput += "\n";
                }
            }
        }

        return consoleOutput;
    }



}
