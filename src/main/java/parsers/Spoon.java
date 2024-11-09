package parsers;


import spoon.Launcher;
import spoon.reflect.CtModel;

public class Spoon extends Parser<Launcher> {

    public Spoon(String projectPath, String jrePath) {
        super(projectPath, jrePath);
    }

    private void setParser() {

        parser = new Launcher();

        parser.addInputResource(projectPath);

        parser.getEnvironment().setComplianceLevel(7);

        parser.getEnvironment().setCommentEnabled(true);

        parser.getEnvironment().setAutoImports(true);

    }


    public CtModel createFAMIXModel() {

        setParser();

        CtModel model = parser.buildModel();

        return model;
    }
}
