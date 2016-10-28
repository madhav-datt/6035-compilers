package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/11/16.
 */
public class IrFieldDeclVar extends IrFieldDecl {
    public IrFieldDeclVar(IrIdent fieldName, IrType fieldType) {
        super(fieldName, fieldType);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        IrType type = this.getType();
        String varLocationInStack = stackFrame.getNextStackLocation();
        if(type instanceof IrTypeBool){
            assembly.addLine("movq $0, %r10");
        }
        if(type instanceof IrTypeInt){
            assembly.addLine("movq $0, %r10");
        }
        assembly.addLine("movq %r10, " + varLocationInStack);
        stackFrame.pushToStackFrame(this.getIdentName());
        assembly.putOnFootNote(varLocationInStack);
        assembly.addLine();
        return assembly;
    }
}
