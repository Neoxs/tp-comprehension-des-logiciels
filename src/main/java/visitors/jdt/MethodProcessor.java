package visitors.jdt;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class MethodProcessor {

    List<MethodDeclaration> methods = new ArrayList<>();

    public int processMethods (CompilationUnit parse) {
        MethodVisitor methodVisitor = new MethodVisitor();
        parse.accept(methodVisitor);
        methods.addAll(methodVisitor.getMethods());
        return methodVisitor.getMethods().size();
    }

    public List<MethodDeclaration> getMethods() {
        return methods;
    }


}
