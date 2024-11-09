package graphs;

import org.eclipse.jdt.core.dom.*;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class GraphTools {

    public GraphTools() { }

    /** Get the name of the class which contains the method
     * resolveBinding.getDeclaringClass() = Resolves and returns the binding for the method or constructor declared in this method or constructor declaration.
    */
    public String getClassName(MethodDeclaration methodDeclaration) {
        if (methodDeclaration == null) {
            System.err.println("Method declaration is null.");
            return ""; // Or handle it as appropriate
        }

        IMethodBinding resolveBinding = methodDeclaration.resolveBinding();
        if ((resolveBinding != null) && (resolveBinding.getDeclaringClass()!= null) ) {
            return methodDeclaration.resolveBinding().getDeclaringClass().getName();

        }
        return "";
    }

    /** Get name and params of method */
    public String getMethodNameAndParams(MethodDeclaration method) {
        return method.getName().getFullyQualifiedName() + method.parameters();
    }

    /** Get the name of method invocation */
    public String getClassOfInvocationedMethod(MethodInvocation methodInvocation) {

        if (methodInvocation == null) {
            System.err.println("MethodInvocation is null.");
            return ""; // Or handle it as appropriate
        }
        Expression expression = methodInvocation.getExpression();

        if (expression != null) {
            ITypeBinding typeBinding = expression.resolveTypeBinding();
           if (typeBinding != null) return typeBinding.getTypeDeclaration().getName();
        } else {
            IMethodBinding methodDeclaration = methodInvocation.resolveMethodBinding();
            if (methodDeclaration == null) {
                System.err.println("Method declaration could not be resolved for: " + methodInvocation);
                return ""; // Or handle this case as needed
            }
            return methodDeclaration.getDeclaringClass().getName();
        }
        return "";
    }

    public String getClassOfInvocationedMethod(CtInvocation ctInvocation){

        if (ctInvocation.getTarget().getType().getSimpleName()!= "void") {
            return ctInvocation.getTarget().getType().getSimpleName();
        }

        return getCurrentClassInvocation(ctInvocation);
    }


    public String getCurrentClassInvocation(CtInvocation ctInvocation) {

        CtElement parent = null;
        CtClass currentClass = null;

        // get the current class
        parent = ctInvocation.getParent();

        while (!(parent instanceof CtClass)) {
            parent = parent.getParent();
        }

        currentClass = (CtClass) parent;
        return currentClass.getQualifiedName();
    }


    public static String getClassFullyQualifiedName(TypeDeclaration typeDeclaration) {
        String name = typeDeclaration.getName().getIdentifier();

        if (typeDeclaration.getRoot().getClass() == CompilationUnit.class) {
            CompilationUnit root = (CompilationUnit) typeDeclaration.getRoot();

            if (root.getPackage() != null)
                name = root.getPackage().getName().getFullyQualifiedName() + "." + name;
        }

        return name;
    }

    public String getMethodInvocationName(MethodInvocation methodInvocation) {

        return methodInvocation.getName().getFullyQualifiedName();
    }

    public String getMethodInvocationName(CtInvocation methodInvocation) {
        if (methodInvocation.getExecutable().getDeclaration() instanceof CtMethod) {
            return ((CtMethod)methodInvocation.getExecutable().getDeclaration()).getSimpleName() ; //+
                    //((CtMethod)methodInvocation.getExecutable().getDeclaration()).getParameters();
        }
        return methodInvocation.toString();
    }





}
