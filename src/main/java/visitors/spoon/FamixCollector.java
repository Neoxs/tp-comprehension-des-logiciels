package visitors.spoon;

import spoon.reflect.CtModel;

import java.util.List;

abstract public class FamixCollector {

    protected CtModel ctModel;

    public FamixCollector(CtModel ctModel) {
       setModel(ctModel);
    }

    public void setModel(CtModel ctModel) { this.ctModel = ctModel; }

    abstract public void configCollector();
}
