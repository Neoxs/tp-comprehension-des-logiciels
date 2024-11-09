package visitors.jdt;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class ClassVisitor extends ASTVisitor {

    List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

    @Override
    public boolean visit(TypeDeclaration node) {
        if( !node.isInterface() ) {
             classes.add(node);
        }
        return super.visit(node);
    }

    public List<TypeDeclaration> getClasses() {
        return classes;
    }
}
