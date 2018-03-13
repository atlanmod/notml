package clang;


import fr.imta.naomod.Context;
import framework.IoTCompiler;
import lang.iotlang.Thing;

public class ClangContext extends Context{
	public ClangContext(IoTCompiler compiler) {
		super(compiler);
		// TODO Auto-generated constructor stub
	}
	protected Thing concreteThing = null;
	
	public void setConcreteThing(Thing t) {
        concreteThing = t;
    }
	public void clearConcreteThing() {
        concreteThing = null;
    }
}
